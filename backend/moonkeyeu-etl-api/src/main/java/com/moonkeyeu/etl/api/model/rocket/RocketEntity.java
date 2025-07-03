package com.moonkeyeu.etl.api.model.rocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "rocket", schema = "moonkey_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketEntity  {
    @Id
    @Column(name = "rocket_id")
    private Long rocket_id;
    private Integer rocket_conf_id;
}
