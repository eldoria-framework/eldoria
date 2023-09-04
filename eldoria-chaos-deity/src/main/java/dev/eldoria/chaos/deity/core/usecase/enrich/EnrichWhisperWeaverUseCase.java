package dev.eldoria.chaos.deity.core.usecase.enrich;

import dev.eldoria.amulet.core.usecase.enrich.Amulet;
import net.dv8tion.jda.api.hooks.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class EnrichWhisperWeaverUseCase {
    private final List<EventListener> eventListeners;

    private final Amulet amulet;

    public EnrichWhisperWeaverUseCase(List<EventListener> eventListeners, Amulet amulet) {
        this.eventListeners = eventListeners;
        this.amulet = amulet;
        this.execute().subscribe();
    }

    public Mono<Void> execute() {
        return Flux.fromIterable(eventListeners)
                .flatMap(amulet::registerListener)
                .then();
    }
}
