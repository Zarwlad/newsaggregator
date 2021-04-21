package com.zarwlad.newsaggregator.docsparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.zarwlad.newsaggregator.docsparser.entity.DataSource;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@JsonAutoDetect
@Data
public class ChangeLogDto {
    @NotNull
    private UUID id;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private String sourceText;

    @NotNull
    private String beatifiedText;

    private LocalDate changeLogDate;

    @NotNull
    private DataSource dataSource;
}
