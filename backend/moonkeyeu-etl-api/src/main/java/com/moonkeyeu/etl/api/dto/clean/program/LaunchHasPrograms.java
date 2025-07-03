package com.moonkeyeu.etl.api.dto.clean.program;

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
public class LaunchHasPrograms implements CsvEntity, PkBuilder {
    private String id;
    private String launch_id;
    private String program_id;
    @Override
    public String getPrimaryKey() {
        setPrimaryKey();
        return id;
    }

    @Override
    public void setPrimaryKey() {
        if(launch_id != null || program_id != null) {
            this.id = launch_id + program_id;
        }
    }
}
