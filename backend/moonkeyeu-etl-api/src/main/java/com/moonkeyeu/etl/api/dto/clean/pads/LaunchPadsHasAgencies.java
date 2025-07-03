package com.moonkeyeu.etl.api.dto.clean.pads;

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
@JsonPropertyOrder({"agency_id", "launch_pad_id"})
public class LaunchPadsHasAgencies implements CsvEntity, PkBuilder {
    private String id;
    @JsonProperty("agency_id")
    private String agency_id;
    @JsonProperty("launch_pad_id")
    private String launch_pad_id;

    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return id;
    }


    @Override
    public void setPrimaryKey() {
        this.id = agency_id + launch_pad_id;
    }
}
