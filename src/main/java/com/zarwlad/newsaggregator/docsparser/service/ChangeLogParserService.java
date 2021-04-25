package com.zarwlad.newsaggregator.docsparser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarwlad.newsaggregator.docsparser.entity.ChangeLog;
import com.zarwlad.newsaggregator.docsparser.model.ParseIterationDto;
import com.zarwlad.newsaggregator.docsparser.repository.ChangeLogRepository;
import com.zarwlad.newsaggregator.docsparser.repository.ParseIterationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeLogParserService {
    private final ChangeLogRepository changeLogRepository;
    private final ParseIterationRepository parseIterationRepository;
    private final ObjectMapper objectMapper;

    public List<ChangeLog> findAll(){
        return changeLogRepository.findAll();
    }

    @SneakyThrows
    @JmsListener(destination = "pdf_queue")
    @Transactional
    public void parseLogFromIteration(String parseIterationDto){
        var dto = objectMapper.readValue(parseIterationDto, ParseIterationDto.class);
        log.info("parseIterationDto: " + parseIterationDto);

        var iteration = parseIterationRepository.findById(dto.getId()).get();

        log.info("iteration: " + iteration.toString());
    }
}
