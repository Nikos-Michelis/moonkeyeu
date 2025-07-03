package com.moonkeyeu.etl.api.model.agency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agencies", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgenciesEntity  {
    @Id
    @Column(name = "agency_id")
    private Long agency_id;
    @Basic
    @Column(name = "name")
    private String name;
    private Integer type_id;
    @Basic
    @Column(name ="featured", columnDefinition = "TINYINT(1)")
    private Boolean featured;
    @Basic
    @Column(name = "abbrev")
    private String abbrev;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "administrator")
    private String administrator;
    @Basic
    @Column(name = "founding_year")
    private String founding_year;
    @Basic
    @Column(name = "launchers")
    private String launchers;
    @Basic
    @Column(name = "spacecraft")
    private String spacecraft;
    @Basic
    @Column(name = "total_launch_count")
    private Integer total_launch_count;
    @Basic
    @Column(name = "consecutive_successful_launches")
    private Integer consecutive_successful_launches;
    @Basic
    @Column(name = "successful_launches")
    private Integer successful_launches;
    @Basic
    @Column(name = "failed_launches")
    private Integer failed_launches;
    @Basic
    @Column(name = "pending_launches")
    private Integer pending_launches;
    @Basic
    @Column(name = "consecutive_successful_landings")
    private Integer consecutive_successful_landings;
    @Basic
    @Column(name = "successful_landings")
    private Integer successful_landings;
    @Basic
    @Column(name = "failed_landings")
    private Integer failed_landings;
    @Basic
    @Column(name = "attempted_landings")
    private Integer attempted_landings;
    @Basic
    @Column(name = "info_url")
    private String info_url;
    @Basic
    @Column(name = "wiki_url")
    private String wiki_url;
}
