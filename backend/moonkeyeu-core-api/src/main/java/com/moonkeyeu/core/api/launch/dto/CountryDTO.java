package com.moonkeyeu.core.api.launch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        {
                "id",
                "name",
                "alpha_2_code",
                "alpha_3_code",
                "nationality_name",
        })
public class CountryDTO {
    @JsonProperty("id")
    private Long countryId;
    @JsonProperty("name")
    private String countryName;
    @JsonProperty("alpha_2_code")
    private String alpha2Code;
    @JsonProperty("alpha_3_code")
    private String alpha3Code;
    @JsonProperty("nationality_name")
    private String nationalityName;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CountryDTO that = (CountryDTO) object;
        return Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId);
    }
}
