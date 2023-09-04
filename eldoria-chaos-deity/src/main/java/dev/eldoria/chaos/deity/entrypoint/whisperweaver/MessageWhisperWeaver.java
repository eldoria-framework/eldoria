package dev.eldoria.chaos.deity.entrypoint.whisperweaver;


import dev.eldoria.amulet.core.model.CodexMagus;
import dev.eldoria.amulet.core.usecase.enrich.EnrichCodex;
import dev.eldoria.chaos.deity.core.model.Wand;
import dev.eldoria.chaos.deity.core.model.Wizard;
import dev.eldoria.chaos.deity.configuration.properties.ChaosDeityProperties;
import dev.eldoria.rune.core.model.RuneResult;
import dev.eldoria.rune.core.usecase.process.rune.ProcessRuneUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@AllArgsConstructor
public class MessageWhisperWeaver extends ListenerAdapter {

    private final ProcessRuneUseCase processRuneUseCase;

    private final EnrichCodex enrichCodex;

    private final ChaosDeityProperties chaosDeityProperties;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        if (event.getAuthor().isBot() || !event.isFromGuild() || !contentRaw.startsWith(chaosDeityProperties.getRunePrefix())) {
            return;
        }
        Wizard wizard = new Wizard(event.getAuthor());
        Wand wand = new Wand(event.getChannel());
        Mono.just(new CodexMagus(wizard, wand))
                .flatMap(codexMagus -> enrichCodex.runWithCodexMagus(codexMagus, runWithSender(event, contentRaw)))
                .onErrorResume(throwable -> Mono.fromRunnable(() ->
                        log.error("Error when processing message", throwable)))
                .subscribe();
    }

    @NotNull
    private Runnable runWithSender(MessageReceivedEvent event, String contentRaw) {
        return () -> {
            final RuneResult runeResult = processRuneUseCase.execute(contentRaw.substring(1).split(" "));
            runeResult.getOutput()
                    .flatMap(result -> Mono.fromRunnable(() -> event.getChannel().sendMessage(result).queue()))
                    .subscribe();
        };
    }

}