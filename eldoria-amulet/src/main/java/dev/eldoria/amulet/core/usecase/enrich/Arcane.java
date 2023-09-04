package dev.eldoria.amulet.core.usecase.enrich;

import dev.eldoria.amulet.core.model.ArcaneRequest;
import dev.eldoria.amulet.core.model.ArcaneResponse;
import reactor.core.publisher.Mono;

public interface Arcane {

    Mono<ArcaneResponse> castSpell(ArcaneRequest arcaneRequest);

}