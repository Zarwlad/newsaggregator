package com.zarwlad.newsaggregator.docsparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.zarwlad.newsaggregator.docsparser.entity.ParsedDocApiType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@JsonAutoDetect
@Data
public class DataSourceDto {
    @NotNull
    private UUID id;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private ParsedDocApiType parsedDocApiType;

    @NotNull
    private Integer pageCount;

    @NotNull
    private byte[] source;
}
