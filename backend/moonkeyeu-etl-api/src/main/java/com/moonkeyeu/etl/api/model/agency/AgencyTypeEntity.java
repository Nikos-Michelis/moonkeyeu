package com.moonkeyeu.etl.api.model.agency;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "agency_type", schema = "moonkey_db")
public class AgencyTypeEntity  {
    @Id
    @Column(name = "type_id", nullable = false)
    @JsonProperty("type_id")
    private Long type_id;

    @Column(name = "name", nullable = false, length = 45)
    @JsonProperty("name")
    private String name;
}
