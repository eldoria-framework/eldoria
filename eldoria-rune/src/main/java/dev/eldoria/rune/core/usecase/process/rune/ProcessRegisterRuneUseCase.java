package dev.eldoria.rune.core.usecase.process.rune;

import dev.eldoria.rune.core.model.RuneNode;
import dev.eldoria.rune.core.usecase.enrich.EnrichRuneLine;
import dev.eldoria.rune.core.usecase.process.AbstractRuneUseCase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessRegisterRuneUseCase extends AbstractRuneUseCase {

    private final ProcessFinderRuneUseCase processFinderCommandUseCase;

    public ProcessRegisterRuneUseCase(ProcessFinderRuneUseCase processFinderCommandUseCase) {
        this.processFinderCommandUseCase = processFinderCommandUseCase;
    }

    public void execute(EnrichRuneLine enrichRuneLine, Collection<String> commands, ApplicationContext applicationContext, CommandLine.IFactory factory) {
        EnrichRuneLine current = enrichRuneLine;
        Map<Class<?>, EnrichRuneLine> parents = new HashMap<>();
        Set<Map.Entry<RuneNode, List<String>>> foundCommands = processFinderCommandUseCase.execute(commands, applicationContext).entrySet();
        for (Map.Entry<RuneNode, List<String>> entry : foundCommands) {
            RuneNode node = entry.getKey();
            if (node.getParent() != null && !node.getParent().equals(getType(applicationContext, current.getBeanName()))) {
                continue;
            }
            List<String> children = entry.getValue();
            String command = node.getBeanName();
            String commandName = getCommandName(node.getClazz());
            if (StringUtils.isBlank(commandName)) {
                continue;
            }
            if (parents.containsKey(node.getParent())) {
                current = parents.get(node.getParent());
            } else if (node.getParent() == null) {
                current = enrichRuneLine;
            }
            if (children.isEmpty()) {
                current.addHarmonyRune(commandName, command);
            } else {
                EnrichRuneLine subEnrichRuneLine = new EnrichRuneLine(command, factory);
                current.addHarmonyRune(commandName, subEnrichRuneLine);
                children.forEach(child -> subEnrichRuneLine
                        .addHarmonyRune(getCommandName(child, applicationContext),  new EnrichRuneLine(child, factory)));

                current = subEnrichRuneLine;
            }
            parents.put(node.getClazz(), current);
        }
    }

    private String getCommandName(String command, ApplicationContext applicationContext) {
        if (command == null) {
            return null;
        }
        return getType(applicationContext, command).getAnnotation(CommandLine.Command.class).name();
    }

    private String getCommandName(Class<?> commandClass) {
        if (commandClass == null) {
            return null;
        }
        return commandClass.getAnnotation(CommandLine.Command.class).name();
    }
}
