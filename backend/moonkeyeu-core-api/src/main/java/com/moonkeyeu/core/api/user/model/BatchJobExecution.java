package com.moonkeyeu.core.api.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "jobExecutionId")
@Entity
@Table(name = "BATCH_JOB_EXECUTION", schema = "moonkey_db")
public class BatchJobExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_EXECUTION_ID")
    private long jobExecutionId;
    @Basic
    @Column(name = "VERSION")
    private Long version;
    @ManyToOne
    @JoinColumn(name = "JOB_INSTANCE_ID")
    private BatchJobInstance jobInstance;
    @Basic
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    @Basic
    @Column(name = "START_TIME")
    private Timestamp startTime;
    @Basic
    @Column(name = "END_TIME")
    private Timestamp endTime;
    @Basic
    @Column(name = "STATUS")
    private String status;
    @Basic
    @Column(name = "EXIT_CODE")
    private String exitCode;
    @Basic
    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
    @Basic
    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;
}
