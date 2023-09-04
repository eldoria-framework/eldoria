package dev.eldoria.amulet.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class EnchantedGuildQuestResponse {


    protected String questId;
    protected String questTitle;
    protected List<String> questDescription;
    protected String questGiver;
    protected String questStatus;
    protected String questAssignedTo;
    protected String questType;
}
