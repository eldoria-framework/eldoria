package dev.eldoria.amulet.core.usecase.enrich;

import dev.eldoria.amulet.core.model.EnchantedGuildBoardRequest;
import dev.eldoria.amulet.core.model.EnchantedGuildQuestRequest;
import dev.eldoria.amulet.core.model.EnchantedGuildQuestResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnchantedGuild {

    Mono<EnchantedGuildQuestResponse> embarkOnQuest(EnchantedGuildQuestRequest enchantedQuestRequest);

    Flux<EnchantedGuildQuestResponse> embarkOnQuests(Flux<EnchantedGuildQuestRequest> enchantedQuestRequests);

    Mono<EnchantedGuildQuestResponse> retrieveQuest(EnchantedGuildQuestRequest enchantedQuestRequest);

    Flux<EnchantedGuildQuestResponse> retrieveQuests(Flux<EnchantedGuildQuestRequest> enchantedQuestRequests);

    Mono<EnchantedGuildQuestResponse> completeQuest(EnchantedGuildQuestRequest enchantedQuestRequest);

    Mono<EnchantedGuildQuestResponse> abandonQuest(EnchantedGuildQuestRequest enchantedQuestRequest);

    Flux<EnchantedGuildQuestResponse> findQuestsInBoard(EnchantedGuildBoardRequest enchantedBoardRequest);

}
