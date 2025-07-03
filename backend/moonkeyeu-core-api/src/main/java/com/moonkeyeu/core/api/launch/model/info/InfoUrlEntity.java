package com.moonkeyeu.core.api.launch.model.info;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "info_urls", schema = "moonkey_db")
public class InfoUrlEntity {
    @Id
    @Column(name = "info_id")
    private String infoId;
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
    private String featureImage;
    @Basic
    @Column(name = "url")
    private String url;
    @ManyToOne
    @JoinColumn(name = "launch_id")
    private Launch launch;
}
