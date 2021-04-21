package com.zarwlad.newsaggregator.docsparser.service;

import com.zarwlad.newsaggregator.docsparser.repository.ParseIterationRepository;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;

@Service
@RequiredArgsConstructor
public class ParseIterationService {
    private final ParseIterationRepository parseIterationRepository;
}
