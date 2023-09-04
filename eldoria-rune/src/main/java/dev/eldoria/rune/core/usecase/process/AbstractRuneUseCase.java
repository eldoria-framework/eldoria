package dev.eldoria.rune.core.usecase.process;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ClassUtils;

import java.util.Objects;

public abstract class AbstractRuneUseCase {

    public Class<?> getType(BeanFactory beanFactory, String beanName) {
        return ClassUtils.getUserClass(Objects.requireNonNull(beanFactory.getType(beanName)));
    }
}
