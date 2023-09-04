package dev.eldoria.amulet.core.usecase.process.amulet;

import dev.eldoria.amulet.core.usecase.enrich.Amulet;
import reactor.core.publisher.Mono;

public class ProcessUpdatePresenceUseCase {

    private final Amulet amulet;

    public ProcessUpdatePresenceUseCase(Amulet amulet) {
        this.amulet = amulet;
    }

    public Mono<String> execute(String text) {
        return Mono.just(text).doOnNext(amulet::updatePresence);
    }
}
