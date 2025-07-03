package com.moonkeyeu.etl.api.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "social_media", schema = "moonkey_db")
public class SocialMediaEntity  {
    @Id
    @Column(name = "social_id")
    private Long social_id;
    @Basic
    @Column(name = "name")
    @JsonProperty("media_name")
    private String name;
    @Basic
    @Column(name = "media_url")
    private String media_url;
    @Basic
    @Column(name = "astronaut_id")
    @JsonProperty("astronaut_id")
    private Integer astronaut_id;
}
