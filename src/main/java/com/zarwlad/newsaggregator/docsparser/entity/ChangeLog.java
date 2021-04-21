package com.zarwlad.newsaggregator.docsparser.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private UUID id;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private String sourceText;

    @NotNull
    private String beatifiedText;

    private LocalDate changeLogDate;

    private String version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_source_id")
    private DataSource dataSource;

    @ManyToOne
    @JoinColumn(name = "parse_iteration_id")
    private ParseIteration parseIteration;
}
