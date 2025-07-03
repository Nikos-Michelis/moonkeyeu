package com.moonkeyeu.core.api.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "jobInstanceId")
@Entity
@Table(name = "BATCH_JOB_INSTANCE", schema = "moonkey_db")
public class BatchJobInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_INSTANCE_ID")
    private long jobInstanceId;
    @Basic
    @Column(name = "VERSION")
    private Long version;
    @Basic
    @Column(name = "JOB_NAME")
    private String jobName;
    @Basic
    @Column(name = "JOB_KEY")
    private String jobKey;

    @OneToMany(mappedBy = "jobInstance")
    private Set<BatchJobExecution> batchJobExecutionSet;
}
