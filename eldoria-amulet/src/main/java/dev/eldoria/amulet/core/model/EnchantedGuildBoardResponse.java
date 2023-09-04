package dev.eldoria.amulet.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class EnchantedGuildBoardResponse {
    protected String boardId;
    protected String boardName;
    protected List<EnchantedGuildQuestResponse> enchantedGuildQuestResponses;
}
