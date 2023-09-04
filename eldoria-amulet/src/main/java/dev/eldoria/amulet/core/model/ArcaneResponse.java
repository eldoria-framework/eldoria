package dev.eldoria.amulet.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArcaneResponse {
    private String status;
    private String message;
    private String statusCode;
}
