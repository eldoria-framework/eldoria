package dev.eldoria.rune.core.usecase.process.rune;

import dev.eldoria.rune.core.annotation.HarmonyRune;
import dev.eldoria.rune.core.usecase.process.AbstractRuneUseCase;
import org.springframework.context.ApplicationContext;

public class ProcessHarmonyRuneUseCase extends AbstractRuneUseCase {

    public boolean execute(ApplicationContext applicationContext, String beanName) {
        Class<?> type = getType(applicationContext, beanName);
        return type.isAnnotationPresent(HarmonyRune.class);
    }
}
