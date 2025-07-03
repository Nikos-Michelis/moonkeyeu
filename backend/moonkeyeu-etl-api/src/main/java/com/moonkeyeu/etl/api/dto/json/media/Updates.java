package com.moonkeyeu.etl.api.dto.json.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Updates {
    private String launch_id;
    @JsonProperty("id")
    private Integer update_id;
    private String profile_image;
    private String comment;
    private String info_url;
    private String created_by;
    private String created_on;
}
