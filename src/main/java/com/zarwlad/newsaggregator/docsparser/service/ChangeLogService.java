package com.zarwlad.newsaggregator.docsparser.service;

import com.zarwlad.newsaggregator.docsparser.repository.ChangeLogRepository;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;

@Service
@RequiredArgsConstructor
public class ChangeLogService {
    private final ChangeLogRepository changeLogRepository;
}
