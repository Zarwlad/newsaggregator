package com.zarwlad.newsaggregator.docsparser.repository;

import com.zarwlad.newsaggregator.docsparser.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, UUID> {
}
