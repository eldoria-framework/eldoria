package dev.eldoria.rune.core.usecase.process.rune;


import dev.eldoria.rune.core.model.RuneResult;
import dev.eldoria.rune.core.usecase.enrich.EnrichRuneLine;
import dev.eldoria.rune.entrypoint.PrimeRune;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@Slf4j
@ConditionalOnBean(annotation = CommandLine.Command.class)
public class ProcessRuneUseCase {

    private final ApplicationContext applicationContext;

    private final AbstractAutoProxyCreator proxyCreator;

    private final EnrichRuneLine enrichRuneLine;

    private CommandLine commandLineCache;

    public ProcessRuneUseCase(ApplicationContext applicationContext, AbstractAutoProxyCreator proxyCreator, EnrichRuneLine enrichRuneLine) {
        this.applicationContext = applicationContext;
        this.proxyCreator = proxyCreator;
        this.enrichRuneLine = enrichRuneLine;
    }

    public RuneResult execute(String... commandParts) {
        if (commandParts.length == 0) {
            return RuneResult.unknown();
        }
        try {
            if (commandLineCache == null) {
                commandLineCache = enrichRuneLine.build(applicationContext);
            }

            Flux<String> output = Flux.empty();
            List<CommandLine> commands = commandLineCache.parse(commandParts);

            if (commands.isEmpty()) {
                return RuneResult.unknown();
            }

            CommandLine commandLine = commands.get(commands.size() - 1);
            Object command = proxyCreator != null ? proxyCreator.getEarlyBeanReference(commandLine.getCommand(), null) : commandLine.getCommand();

            if (command instanceof Runnable runnable) {
                runnable.run();
            }

            if (command instanceof Callable<?>) {
                Object result = ((Callable<?>) command).call();
                output = output.concatWith(buildOutput(result));
            }

            return new RuneResult(output);
        } catch (CommandLine.InitializationException ex) {
            log.error("Unexpected exception during command initialization", ex);
            return RuneResult.unknown();
        } catch (CommandLine.UnmatchedArgumentException ex) {
            Object commandObject = ex.getCommandLine().getCommandSpec().userObject();
            if (commandObject == null || getBaseCommandClass().isInstance(commandObject)){
                return RuneResult.unknown();
            }
            Mono<String> message = Mono.just(String.format("Invalid parameter: %s", String.join(", ", ex.getUnmatched())));
            return new RuneResult(message, true);
        } catch (CommandLine.MissingParameterException ex) {
            Mono<String> message = Mono.just(String.format("Missing parameter: %s", ex.getMissing().get(0).paramLabel()));
            return new RuneResult(message, true);
        } catch (CommandLine.ParameterException ex) {
            Mono<String> message = Mono.just(String.format("Invalid parameter: %s", ex.getArgSpec().paramLabel()));
            return new RuneResult(message, true);
        } catch (Exception ex) {
            log.error("Unexpected exception while running /" + StringUtils.join(commandParts, " "), ex);
            return new RuneResult(Mono.just("An internal error occurred while attempting to perform this"), true);
        }
    }

    private Flux<String> buildOutput(Object result) {
        if (result instanceof Mono<?> mono) {
            return mono.cast(String.class).flux();
        } else if (result instanceof Flux<?> flux) {
            return flux.cast(String.class).filter(Objects::nonNull);
        }
        return Flux.empty();
    }

    @SneakyThrows
    private Class<?> getBaseCommandClass() {
        return Class.forName(PrimeRune.class.getName());
    }

}