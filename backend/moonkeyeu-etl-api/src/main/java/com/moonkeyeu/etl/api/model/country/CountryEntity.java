package com.moonkeyeu.etl.api.model.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "country", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryEntity  {
    @Id
    @Column(name = "country_id")
    private Long country_id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "alpha_2_code")
    private String alpha_2_code;
    @Basic
    @Column(name = "alpha_3_code")
    private String alpha_3_code;
    @Basic
    @Column(name = "nationality_name")
    private String nationality_name;
    @Basic
    @Column(name = "nationality_name_composed")
    private String nationality_name_composed;
}
