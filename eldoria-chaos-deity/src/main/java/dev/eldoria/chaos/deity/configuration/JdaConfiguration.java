package dev.eldoria.chaos.deity.configuration;

import dev.eldoria.chaos.deity.configuration.properties.ChaosDeityProperties;
import dev.eldoria.chaos.deity.entrypoint.whisperweaver.ReadyWhisperWeaver;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.eldoria.chaos.deity.configuration.constants.ConstantHelper.TWITCH_TV_ELDORIA;

@Configuration
@RequiredArgsConstructor
public class JdaConfiguration {

    private final ChaosDeityProperties chaosDeityProperties;
    @Bean
    public JDA jda() {
        return JDABuilder.createDefault(chaosDeityProperties.getToken())
                .addEventListeners(new ReadyWhisperWeaver())
                .setActivity(Activity.streaming(chaosDeityProperties.getDefaultStatus(), TWITCH_TV_ELDORIA))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }
}
