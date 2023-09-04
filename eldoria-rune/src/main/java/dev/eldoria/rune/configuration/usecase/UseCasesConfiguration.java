package dev.eldoria.rune.configuration.usecase;



import dev.eldoria.rune.core.usecase.enrich.EnrichRuneForgeUseCase;
import dev.eldoria.rune.core.usecase.enrich.EnrichRuneLine;
import dev.eldoria.rune.core.usecase.process.rune.ProcessFinderRuneUseCase;
import dev.eldoria.rune.core.usecase.process.rune.ProcessHarmonyRuneUseCase;
import dev.eldoria.rune.core.usecase.process.rune.ProcessMainRuneUseCase;
import dev.eldoria.rune.core.usecase.process.rune.ProcessRegisterRuneUseCase;
import dev.eldoria.rune.core.usecase.process.rune.ProcessRuneUseCase;
import dev.eldoria.rune.entrypoint.PrimeRune;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration("RuneUseCasesConfiguration")
@Scope(SCOPE_SINGLETON)
public class UseCasesConfiguration {

    @Bean
    public EnrichRuneForgeUseCase enrichRuneForgeUseCase(BeanFactory beanFactory) {
        return new EnrichRuneForgeUseCase(beanFactory);
    }

    @Bean
    @Primary
    public ProcessRuneUseCase processRuneUseCase(ApplicationContext applicationContext, AbstractAutoProxyCreator proxyCreator, EnrichRuneLine enrichRuneLine) {
        return new ProcessRuneUseCase(applicationContext, proxyCreator, enrichRuneLine);
    }

    @Bean
    public ProcessMainRuneUseCase processMainRuneUseCase() {
        return new ProcessMainRuneUseCase();
    }

    @Bean
    public ProcessHarmonyRuneUseCase processHarmonyRuneUseCase() {
        return new ProcessHarmonyRuneUseCase();
    }

    @Bean
    public ProcessFinderRuneUseCase processFinderRuneUseCase() {
        return new ProcessFinderRuneUseCase();
    }

    @Bean
    public ProcessRegisterRuneUseCase processRegisterRuneUseCase(ProcessFinderRuneUseCase processFinderRuneUseCase) {
        return new ProcessRegisterRuneUseCase(processFinderRuneUseCase);
    }

    @Bean
    EnrichRuneLine enrichRuneLine(ApplicationContext applicationContext, CommandLine.IFactory factory,
            ProcessRegisterRuneUseCase processRegisterRuneUseCase,
            ProcessMainRuneUseCase processMainRuneUseCase,
            ProcessHarmonyRuneUseCase processHarmonyRuneUseCase) {

        List<String> runes = Arrays.stream(applicationContext.getBeanNamesForAnnotation(CommandLine.Command.class))
                .filter(name -> !processHarmonyRuneUseCase.execute(applicationContext, name))
                .collect(Collectors.toList());

        List<String> mainRunes = processMainRuneUseCase.execute(runes, applicationContext);
        Object mainCommand = mainRunes.isEmpty() ? new PrimeRune() : mainRunes.get(0);
        runes.removeAll(mainRunes);

        EnrichRuneLine enrichRuneLine = new EnrichRuneLine(mainCommand, factory);
        processRegisterRuneUseCase.execute(enrichRuneLine, runes, applicationContext, factory);
        return enrichRuneLine;
    }

}