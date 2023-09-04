package dev.eldoria.rune.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuneNode {

    private Class<?> clazz;

    private String beanName;

    private Class<?> parent;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof RuneNode node)) return false;

        return Objects.equals(clazz, node.clazz);
    }

    @Override
    public int hashCode() {
        return clazz != null ? clazz.hashCode() : 0;
    }
}