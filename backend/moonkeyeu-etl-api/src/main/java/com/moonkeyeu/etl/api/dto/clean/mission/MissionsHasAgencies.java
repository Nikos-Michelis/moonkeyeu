package com.moonkeyeu.etl.api.dto.clean.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.PkBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"agency_id", "mission_id"})
public class MissionsHasAgencies implements CsvEntity, PkBuilder {
    private String id;
    @JsonProperty("agency_id")
    private String agency_id;
    @JsonProperty("mission_id")
    private String mission_id;

    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return id;
    }
    @Override
    public void setPrimaryKey() {
        this.id = agency_id + mission_id;
    }
}
