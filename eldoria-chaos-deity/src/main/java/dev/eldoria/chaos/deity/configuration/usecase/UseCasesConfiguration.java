package dev.eldoria.chaos.deity.configuration.usecase;

import dev.eldoria.amulet.core.usecase.enrich.Amulet;
import dev.eldoria.chaos.deity.core.usecase.enrich.EnrichWhisperWeaverUseCase;
import dev.eldoria.chaos.deity.entrypoint.whisperweaver.MessageWhisperWeaver;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("ChaosDeityUseCasesConfiguration")
public class UseCasesConfiguration {

    @Bean
    public EnrichWhisperWeaverUseCase enrichWhisperWeaverUseCase(List<EventListener> eventListeners, Amulet amulet, MessageWhisperWeaver messageListener) {
        return new EnrichWhisperWeaverUseCase(eventListeners, amulet);
    }
}