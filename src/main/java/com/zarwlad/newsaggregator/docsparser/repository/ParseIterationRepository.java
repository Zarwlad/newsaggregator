package com.zarwlad.newsaggregator.docsparser.repository;

import com.zarwlad.newsaggregator.docsparser.entity.ParseIteration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParseIterationRepository extends JpaRepository<ParseIteration, UUID> {
}
