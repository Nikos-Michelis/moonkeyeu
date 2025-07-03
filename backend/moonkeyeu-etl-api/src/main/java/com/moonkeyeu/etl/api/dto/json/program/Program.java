package com.moonkeyeu.etl.api.dto.json.program;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moonkeyeu.etl.api.dto.json.Images.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {
    @JsonProperty("id")
    private Integer program_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private Image image;
    private String info_url;
    private String wiki_url;
    private String description;
    private String start_date;
    @JsonProperty("type")
    private ProgramType type;
    private String launch_id;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ProgramType {
    @JsonProperty("id")
    private Integer type_id;
    @JsonProperty("name")
    private String type_name;
}