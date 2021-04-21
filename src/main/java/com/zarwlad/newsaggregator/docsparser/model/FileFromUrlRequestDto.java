package com.zarwlad.newsaggregator.docsparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.zarwlad.newsaggregator.docsparser.entity.ParsedDocApiType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.net.URL;

@JsonAutoDetect
@Data
public class FileFromUrlRequestDto {
    @NotNull
    private URL url;

    @NotNull
    private String stopPhrase;

    @NotNull
    private ParsedDocApiType parsedDocApiType;
}
