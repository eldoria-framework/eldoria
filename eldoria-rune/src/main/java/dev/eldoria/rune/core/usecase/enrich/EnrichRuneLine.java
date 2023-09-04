package dev.eldoria.rune.core.usecase.enrich;


import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import picocli.CommandLine;
import picocli.CommandLine.IHelpSectionRenderer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class EnrichRuneLine {

    private final String beanName;
    private final Object instance;
    private final CommandLine.IFactory commandFactory;

    private final HashMap<String, EnrichRuneLine> harmonyRunes = new HashMap<>();

    public EnrichRuneLine(Object instance, CommandLine.IFactory factory) {
        this.beanName = instance instanceof String ? (String) instance : null;
        this.instance = !(instance instanceof String) ? instance : null;
        this.commandFactory = factory;
    }

    public void addHarmonyRune(String name, Object commandLine) {
        if (commandLine instanceof EnrichRuneLine) {
            harmonyRunes.put(name, (EnrichRuneLine) commandLine);
        } else {
            harmonyRunes.put(name, new EnrichRuneLine(commandLine, commandFactory));
        }
    }

    public CommandLine build(BeanFactory factory) {
        CommandLine commandLine = doBuild(factory);

        overrideHelpRenderers(commandLine);
        return commandLine;
    }

    private CommandLine doBuild(BeanFactory factory) {
        CommandLine commandLine = new CommandLine(beanName != null ? getBean(factory, beanName) : instance, commandFactory);

        harmonyRunes.forEach((key, value) -> commandLine.addSubcommand(key, value.doBuild(factory)));
        return commandLine;
    }

    @SneakyThrows
    private Object getBean(BeanFactory factory, String name) {
        Object bean = factory.getBean(name);
        if (AopUtils.isAopProxy(bean)) {
            return ((Advised) bean).getTargetSource().getTarget();
        }
        return bean;
    }

    public Set<String> getRunesNames() {
        return Collections.unmodifiableSet(harmonyRunes.keySet());
    }


    private void overrideHelpRenderers(CommandLine commandLine) {
        Map<String, IHelpSectionRenderer> renderers = commandLine.getHelpSectionMap().keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(), key -> overrideRenderer(commandLine.getHelpSectionMap().get(key))));
        commandLine.setHelpSectionMap(renderers);
    }

    private IHelpSectionRenderer overrideRenderer(IHelpSectionRenderer renderer) {
        return (h) -> renderer.render(h).replaceAll("\\r", "");
    }

}