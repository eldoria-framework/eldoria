package dev.eldoria.chaos.deity.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("eldoria.chaos.deity")
public class ChaosDeityProperties {
    private String runePrefix;
    private String defaultStatus;
    private String token;
    private List<String> presenceMessages;
}