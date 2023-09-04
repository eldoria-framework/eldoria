package dev.eldoria.amulet.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class EnchantedGuildQuestRequest {

    protected String questId;
    protected String questGiver;
    protected String questStatus;
    protected String questAssignedTo;
    protected String questType;
}
