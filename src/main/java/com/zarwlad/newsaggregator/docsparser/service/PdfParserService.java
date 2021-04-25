package com.zarwlad.newsaggregator.docsparser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarwlad.newsaggregator.docsparser.entity.DataSource;
import com.zarwlad.newsaggregator.docsparser.entity.ParseIteration;
import com.zarwlad.newsaggregator.docsparser.model.FileFromUrlRequestDto;
import com.zarwlad.newsaggregator.docsparser.model.ParseIterationDto;
import com.zarwlad.newsaggregator.docsparser.parser.PdfDocParser;
import com.zarwlad.newsaggregator.docsparser.repository.DataSourceRepository;
import com.zarwlad.newsaggregator.docsparser.repository.ParseIterationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfParserService {
    private final DataSourceRepository dataSourceRepository;
    private final ParseIterationRepository parseIterationRepository;
    private final PdfDocParser pdfDocParser;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public ParseIterationDto save(FileFromUrlRequestDto fileFromUrlRequestDto){
        var rawFile = pdfDocParser.readBytesFromFile(fileFromUrlRequestDto.getUrl());
        var pdfReader = pdfDocParser.getPdfReaderInstance(rawFile);

        var dataSource = new DataSource();
        dataSource.setPageCount(pdfReader.getNumberOfPages());
        dataSource.setFilename(URLDecoder.decode(fileFromUrlRequestDto.getUrl().getFile(), StandardCharsets.UTF_8));
        dataSource.setCreatedAt(ZonedDateTime.now());
        dataSource.setOriginalPath(fileFromUrlRequestDto.getUrl());
        dataSource.setParsedDocApiType(fileFromUrlRequestDto.getParsedDocApiType());
        dataSource.setSource(rawFile);
        dataSource.setLength(pdfReader.getFileLength());


        var parseIteration = new ParseIteration();
        parseIteration.setCreatedAt(ZonedDateTime.now());
        parseIteration.setStopPhrase(fileFromUrlRequestDto.getStopPhrase());
        parseIteration.setDataSource(dataSource);

        dataSourceRepository.save(dataSource);
        parseIterationRepository.save(parseIteration);

        var parseIterationDto = new ParseIterationDto(
                parseIteration.getId(),
                parseIteration.getCreatedAt(),
                parseIteration.getStopPhrase(),
                parseIteration.getDataSource().getId()
        );

        try {
            String destination = "pdf_queue";
            jmsTemplate.convertAndSend(destination, objectMapper.writeValueAsString(parseIterationDto));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return parseIterationDto;
    }
}
