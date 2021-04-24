package com.zarwlad.newsaggregator.docsparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zarwlad.newsaggregator.docsparser.entity.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@JsonAutoDetect
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseIterationDto {
    @NotNull
    private UUID id;

    @NotNull
    private ZonedDateTime createdAt;

    private String stopPhrase;

    @JsonIgnore
    private DataSource dataSource;
}
