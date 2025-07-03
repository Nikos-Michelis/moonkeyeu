package com.moonkeyeu.etl.api.dto.json.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyHasCountry {
    private Integer agency_id;
    private Integer country_id;
}
