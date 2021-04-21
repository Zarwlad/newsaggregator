package com.zarwlad.newsaggregator.docsparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonAutoDetect
@Data
@AllArgsConstructor
public class RawStringResponseDto {
    @NotNull
    private String answer;
}
