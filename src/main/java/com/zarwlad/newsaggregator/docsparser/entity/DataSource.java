package com.zarwlad.newsaggregator.docsparser.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
public class DataSource {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ParsedDocApiType parsedDocApiType;

    @NotNull
    private Integer pageCount;

    private URL originalPath;

    private String filename;

    @NotNull
    @ToString.Exclude
    private byte[] source;

    @NotNull
    private Long length;
}
