package com.moonkeyeu.etl.api.model.media;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moonkeyeu.etl.api.utils.TimestampDeserializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Setter
@Getter
@Entity
@Table(name = "updates", schema = "moonkey_db")
public class UpdatesEntity  {
    @Id
    @Column(name = "update_id")
    private Long update_id;
    @Basic
    @Column(name = "profile_image")
    private String profile_image;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "info_url")
    private String info_url;
    @Basic
    @Column(name = "created_by")
    private String created_by;
    @Basic
    @Column(name = "created_on")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp created_on;
    @Basic
    @Column(name = "launch_id")
    private String launch_id;
}
