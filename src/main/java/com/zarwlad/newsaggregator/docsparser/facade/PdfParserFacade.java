package com.zarwlad.newsaggregator.docsparser.facade;

import com.itextpdf.text.pdf.PdfReader;
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
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfParserFacade {
    private final DataSourceRepository dataSourceRepository;
    private final ParseIterationRepository parseIterationRepository;
    private final PdfDocParser pdfDocParser;
    //private final ModelMapper modelMapper;

    @Transactional
    public ParseIterationDto save(FileFromUrlRequestDto fileFromUrlRequestDto){
        var rawFile = pdfDocParser.readBytesFromFile(fileFromUrlRequestDto.getUrl());
        var pdfReader = pdfDocParser.getPdfReaderInstance(rawFile);

        var dataSource = new DataSource();
        dataSource.setPageCount(pdfReader.getNumberOfPages());
        dataSource.setFilename(fileFromUrlRequestDto.getUrl().getFile());
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

        return new ParseIterationDto(parseIteration.getId(), parseIteration.getCreatedAt(), parseIteration.getStopPhrase(), parseIteration.getDataSource());
    }
}
