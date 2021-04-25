package com.zarwlad.newsaggregator.docsparser.rest;

import com.zarwlad.newsaggregator.docsparser.service.PdfParserService;
import com.zarwlad.newsaggregator.docsparser.model.FileFromUrlRequestDto;
import com.zarwlad.newsaggregator.docsparser.model.ParseIterationDto;
import com.zarwlad.newsaggregator.docsparser.model.RawStringResponseDto;
import com.zarwlad.newsaggregator.docsparser.parser.PdfDocParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController("/parser")
@RequiredArgsConstructor
@Slf4j
public class PdfDocParserController {
    private final PdfDocParser pdfDocParser;
    private final PdfParserService pdfParserService;

    @PostMapping(value = "/parse-to-text-from-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RawStringResponseDto parseToText(@RequestParam("file") MultipartFile file, @NotNull String stopPhrase) {
        return new RawStringResponseDto(pdfDocParser.parseChangeLog(file, stopPhrase));
    }

    @PostMapping(value = "/from-link")
    public ParseIterationDto parseToTextFromUrl(@RequestBody FileFromUrlRequestDto fileFromUrlRequestDto){
        return pdfParserService.save(fileFromUrlRequestDto);
    }
}
