package dev.eldoria.chaos.deity.dataprovider.amulet;

import dev.eldoria.amulet.core.model.Mage;
import dev.eldoria.amulet.core.usecase.enrich.Amulet;
import dev.eldoria.chaos.deity.core.model.Wizard;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.eldoria.chaos.deity.configuration.constants.ConstantHelper.TWITCH_TV_ELDORIA;

@Component
@AllArgsConstructor
public class AmuletOfChaosDeity implements Amulet {

    private final JDA jda;

    @Override
    public Mono<Mage> getSenderById(String id) {
        return Mono.justOrEmpty(id)
                .mapNotNull(jda::getUserById).map(Wizard::new);
    }

   @Override
    public Flux<Mage> getSendersByName(String name) {
        return Flux.fromIterable(jda.getUsersByName(name, true))
                .map(Wizard::new);
    }

    @Override
    public Mono<Void> registerListener(Object... listeners) {
        return Mono.fromRunnable(() -> jda.addEventListener(listeners));
    }

    @Override
    public Mono<Void> updatePresence(String text) {
        return Mono.fromRunnable(() -> jda.getPresence()
                .setPresence(Activity.streaming(text, TWITCH_TV_ELDORIA), true));
    }
}
