package com.moonkeyeu.core.api.user.services;

import com.moonkeyeu.core.api.user.dto.BatchJobExecDTO;

import java.util.List;

public interface BatchService {
    List<BatchJobExecDTO> getAllBatchJobs();
}
