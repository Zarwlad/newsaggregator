package com.zarwlad.newsaggregator.docsparser.service;

import com.zarwlad.newsaggregator.docsparser.repository.DataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;

@Service
@RequiredArgsConstructor
public class DataSourceService {
    private final DataSourceRepository dataSourceRepository;
}
