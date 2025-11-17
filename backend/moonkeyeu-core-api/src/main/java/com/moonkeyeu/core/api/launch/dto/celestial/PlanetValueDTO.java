package com.moonkeyeu.core.api.launch.dto.celestial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetValueDTO {
    private Object value;
    private String unit;
    private String readable;
}
