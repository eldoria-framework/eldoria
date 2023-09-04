package dev.eldoria.amulet.core.usecase.enrich;

import dev.eldoria.amulet.core.model.Mage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Amulet {

    Mono<Mage> getSenderById(String id);

    Flux<Mage> getSendersByName(String name);

    Mono<Void> registerListener(Object... objects);

    Mono<Void> updatePresence(String text);
}
