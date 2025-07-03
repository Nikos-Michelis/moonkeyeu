package com.moonkeyeu.core.api.launch.dto.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "info_url", "wiki_url", "description", "start_date", "type", "programImages"})
public class ProgramSummarizedDTO implements DTOEntity {
    @JsonProperty("id")
    private Long programId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("info_url")
    private String infoUrl;
    @JsonProperty("wiki_url")
    private String wikiUrl;
    @JsonProperty("description")
    private String description;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty ("type")
    private String typeName;
    @JsonProperty("images")
    private Set<ImageDTO> programImages;
}
