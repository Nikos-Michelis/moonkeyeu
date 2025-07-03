package com.moonkeyeu.core.api.launch.model.views;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseFilter {
    @Id
    @Column(name = "virtual_id")
    private String id;

    @Basic
    @Column(name = "filter_id")
    private Integer filterId;

    @Basic
    @Column(name = "filter_name")
    private String filterName;

    @Basic
    @Column(name = "filter_type")
    private String filterType;
}
