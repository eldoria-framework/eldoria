package dev.eldoria.rune.core.usecase.enrich;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import picocli.CommandLine;


public class EnrichRuneForgeUseCase implements CommandLine.IFactory {

    private final BeanFactory beanFactory;

    public EnrichRuneForgeUseCase(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        K bean = beanFactory.getBean(cls);
        if (!(bean instanceof Iterable) && AopUtils.isAopProxy(bean)) {
            return (K) ((Advised) bean).getTargetSource().getTarget();
        }
        return bean;
    }

}