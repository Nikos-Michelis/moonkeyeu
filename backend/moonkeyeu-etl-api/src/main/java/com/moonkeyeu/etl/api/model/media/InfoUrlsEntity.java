package com.moonkeyeu.etl.api.model.media;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "info_urls", schema = "moonkey_db")
public class InfoUrlsEntity  {
    @Id
    @Column(name = "info_id")
    private String info_id;
    @Basic
    @Column(name = "priority")
    private Integer priority;
    @Basic
    @Column(name = "source")
    private String source;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "feature_image")
    private String feature_image;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
