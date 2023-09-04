package dev.eldoria.chaos.deity.entrypoint.whisperweaver;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;


@Slf4j
public class ReadyWhisperWeaver implements EventListener {

	@Override
	public void onEvent(@NotNull GenericEvent event) {
		if (event instanceof ReadyEvent) {
			log.info("Chaos Deity is ready!");
		}
	}

}