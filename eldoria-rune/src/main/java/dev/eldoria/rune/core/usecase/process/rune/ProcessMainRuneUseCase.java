package dev.eldoria.rune.core.usecase.process.rune;

import dev.eldoria.rune.core.usecase.process.AbstractRuneUseCase;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class ProcessMainRuneUseCase extends AbstractRuneUseCase {

    public List<String> execute(Collection<String> candidates, ApplicationContext context) {
        Method defaultNameMethod = CommandLine.Command.class.getDeclaredMethods()[0];

        return candidates.stream()
                .filter(candidate -> {
                    Class<?> clazz = getType(context, candidate);
                    return clazz.isAnnotationPresent(CommandLine.Command.class) &&
                            defaultNameMethod.getDefaultValue().equals(clazz.getAnnotation(CommandLine.Command.class).name());
                }).toList();
    }
}
