package com.moonkeyeu.etl.api.model.programs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programs_images", schema = "moonkey_db")
public class ProgramsImagesEntity  {
    @Id
    @Column(name = "image_id")
    private Long image_id;
    @Basic
    @Column(name = "image_name")
    private String image_name;
    @Basic
    @Column(name = "image_url")
    private String image_url;
    @Basic
    @Column(name = "thumbnail_url")
    private String thumbnail_url;
    @Basic
    @Column(name = "credit")
    private String credit;
    @Basic
    @Column(name = "program_id")
    private Integer program_id;
}
