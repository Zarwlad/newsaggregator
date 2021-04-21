package com.zarwlad.newsaggregator.docsparser.rest;

import com.zarwlad.newsaggregator.docsparser.model.FileFromUrlRequestDto;
import com.zarwlad.newsaggregator.docsparser.model.RawStringResponseDto;
import com.zarwlad.newsaggregator.docsparser.parser.PdfDocParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController("/docs-parser")
@RequiredArgsConstructor
@Slf4j
public class PdfDocParserController {
    private final PdfDocParser pdfDocParser;

    @PostMapping(value = "/parse-to-text-from-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RawStringResponseDto parseToText(@RequestParam("file") MultipartFile file, @NotNull String stopPhrase) {
        return new RawStringResponseDto(pdfDocParser.parseChangeLog(file, stopPhrase));
    }

    @PostMapping(value = "/parse-to-text-from-link", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RawStringResponseDto parseToTextFromUrl(@RequestBody FileFromUrlRequestDto fileFromUrlRequestDto){
        log.info(fileFromUrlRequestDto.toString());
        return new RawStringResponseDto(pdfDocParser.parseChangeLog(fileFromUrlRequestDto.getUrl(), fileFromUrlRequestDto.getStopPhrase()));
    }
}
