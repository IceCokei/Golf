package org.example.golf.repository;

import org.example.golf.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByType(String type, Pageable pageable);
    
    Page<Log> findByUsername(String username, Pageable pageable);
    
    Page<Log> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
} 