package com.zarwlad.newsaggregator.docsparser.rest;

import com.zarwlad.newsaggregator.docsparser.parser.PdfDocParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("/docs-parser")
@RequiredArgsConstructor
public class PdfDocParserController {
    private final PdfDocParser pdfDocParser;

    @PostMapping(value = "/parse-to-html-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity parseToHtmlFile(@RequestParam("file") MultipartFile file) {
        pdfDocParser.generateTextFromPdf(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/parse-to-text-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String parseToTextFile(@RequestParam("file") MultipartFile file, String stopPhrase) {
        return pdfDocParser.parseChangeLog(file, stopPhrase);
        //return ResponseEntity.ok().build();
    }
}
