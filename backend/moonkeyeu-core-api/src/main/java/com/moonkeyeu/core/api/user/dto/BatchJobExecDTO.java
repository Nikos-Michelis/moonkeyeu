package com.moonkeyeu.core.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchJobExecDTO {
    private long jobExecutionId;
    private Long version;
    private String jobName;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String exitMessage;
    private Timestamp lastUpdated;
}
