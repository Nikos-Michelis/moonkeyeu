package com.moonkeyeu.etl.api.model.media;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "video_url", schema = "moonkey_db")
public class VideoEntity  {
    @Id
    @Column(name = "video_id")
    private String video_id;
    @Basic
    @Column(name = "priority")
    private String priority;
    @Basic
    @Column(name = "source")
    private String source;
    @Basic
    @Column(name = "publisher")
    private String publisher;
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
    @Column(name = "video_url")
    private String video_url;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
