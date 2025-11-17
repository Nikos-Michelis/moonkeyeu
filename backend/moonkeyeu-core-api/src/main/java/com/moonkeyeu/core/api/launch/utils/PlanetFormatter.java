package com.moonkeyeu.core.api.launch.utils;

import com.moonkeyeu.core.api.launch.dto.celestial.PlanetValueDTO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
public class PlanetFormatter {
    private final int KM = 1000;
    private final int BILLION = 1_000_000_000;
    private final int MILLION = 1_000_000;

    public PlanetValueDTO mass(BigDecimal kg) {
        int exponent = kg.precision() - kg.scale() - 1;
        BigDecimal mantissa = kg.movePointLeft(exponent);
        return new PlanetValueDTO(
                kg,
                "kg",
                mantissa.setScale(4, RoundingMode.HALF_UP).toPlainString()
                        + " × 10^" + exponent + " kg"
        );
    }

    public PlanetValueDTO diameter(BigDecimal km) {
        return new PlanetValueDTO(
                km, 
                "km",
                km.longValue() >= KM ? (km.longValue() / KM) + " thousand km" : km + " km"
        );
    }

    public PlanetValueDTO distance(BigDecimal km) {
        String readable =
                km.longValue() >= BILLION ? (km.longValue() / BILLION) + " billion km"
              : km.longValue() >= MILLION ? (km.longValue() / MILLION) + " million km"
              : km + " km";

        return new PlanetValueDTO(km, "km", readable);
    }

    public PlanetValueDTO gravity(BigDecimal g) {
        return new PlanetValueDTO(g, "m/s²", g + " m/s²");
    }

    public PlanetValueDTO tilt(BigDecimal deg) {
        return new PlanetValueDTO(deg, "°", deg + "°");
    }

    public PlanetValueDTO rotation(BigDecimal hours) {
        return new PlanetValueDTO(hours, "hours", hours + " h");
    }

    public PlanetValueDTO orbit(Integer days) {
        return new PlanetValueDTO(days, "days", days + " days");
    }
}
