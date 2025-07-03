package com.moonkeyeu.etl.api.dto.clean.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Updates implements CsvEntity {
    @JsonProperty("update_id")
    private String update_id;
    private String profile_image;
    private String comment;
    private String info_url;
    private String created_by;
    private String created_on;
    @JsonProperty("launch_id")
    private String launch_id;

    @Override
    public String getPrimaryKey() {
        return update_id;
    }

}
