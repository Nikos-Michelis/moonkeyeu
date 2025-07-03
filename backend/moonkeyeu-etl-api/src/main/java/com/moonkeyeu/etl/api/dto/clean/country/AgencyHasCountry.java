package com.moonkeyeu.etl.api.dto.clean.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.PkBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgencyHasCountry implements CsvEntity, PkBuilder {
    private String id;
    private String agency_id;
    private String country_id;
    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return id;
    }
    @Override
    public void setPrimaryKey() {
        this.id = agency_id + country_id;
    }
}
