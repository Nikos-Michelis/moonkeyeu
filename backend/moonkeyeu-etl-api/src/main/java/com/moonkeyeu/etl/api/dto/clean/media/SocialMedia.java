package com.moonkeyeu.etl.api.dto.clean.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialMedia implements CsvEntity {
    @JsonProperty("social_id")
    private String social_id;
    private String media_name;
    @JsonProperty("media_url")
    private String media_url;
    private Integer astronaut_id;

    @Override
    public String getPrimaryKey() {
        return social_id;
    }
}
