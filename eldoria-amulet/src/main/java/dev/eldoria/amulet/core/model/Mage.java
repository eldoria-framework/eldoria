package dev.eldoria.amulet.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Mage {
    protected String id;
    protected String name;

    public abstract void sendMessage(String message);
}
