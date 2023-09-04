package dev.eldoria.rune.core.usecase.process.rune;

import dev.eldoria.rune.core.model.RuneNode;
import dev.eldoria.rune.core.usecase.process.AbstractRuneUseCase;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProcessFinderRuneUseCase extends AbstractRuneUseCase {

    public Map<RuneNode, List<String>> execute(Collection<String> commands, ApplicationContext applicationContext) {
        Map<RuneNode, List<String>> tree = new LinkedHashMap<>();

        commands.stream().filter(o -> getType(applicationContext, o) != null)
                .sorted(Comparator.comparingInt(o -> getNestedLevel(getType(applicationContext, o))))
                .forEach(o -> {
                    Class<?> clazz = getType(applicationContext, o);
                    Optional<Class> parentClass = getParentClass(clazz);
                    parentClass.ifPresent(c -> tree.computeIfAbsent(new RuneNode(c, null, null), k -> new ArrayList<>()).add(o));
                    tree.putIfAbsent(new RuneNode(clazz, o, parentClass.orElse(null)), new ArrayList<>());
                });

        return tree;
    }

    private int getNestedLevel(Class clazz) {
        int level = 0;
        Class parent = clazz.getEnclosingClass();
        while (parent != null && parent.isAnnotationPresent(CommandLine.Command.class)) {
            parent = parent.getEnclosingClass();
            level += 1;
        }
        return level;
    }

    private Optional<Class> getParentClass(Class clazz) {
        Class parentClass = clazz.getEnclosingClass();
        if (parentClass == null || !parentClass.isAnnotationPresent(CommandLine.Command.class)) {
            return Optional.empty();
        }
        return Optional.of(parentClass);
    }
}
