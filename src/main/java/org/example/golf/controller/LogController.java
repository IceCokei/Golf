package org.example.golf.controller;

import org.example.golf.model.Log;
import org.example.golf.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/login")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getLoginLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Log> logs;

        if (username != null && !username.isEmpty()) {
            logs = logRepository.findByUsername(username, paging);
        } else if (startDate != null && endDate != null) {
            logs = logRepository.findByCreatedAtBetween(startDate, endDate, paging);
        } else {
            logs = logRepository.findByType("LOGIN", paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("logs", logs.getContent());
        response.put("currentPage", logs.getNumber());
        response.put("totalItems", logs.getTotalElements());
        response.put("totalPages", logs.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/operations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getOperationLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Log> logs;

        if (type != null && !type.isEmpty()) {
            logs = logRepository.findByType(type, paging);
        } else if (username != null && !username.isEmpty()) {
            logs = logRepository.findByUsername(username, paging);
        } else if (startDate != null && endDate != null) {
            logs = logRepository.findByCreatedAtBetween(startDate, endDate, paging);
        } else {
            logs = logRepository.findAll(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("logs", logs.getContent());
        response.put("currentPage", logs.getNumber());
        response.put("totalItems", logs.getTotalElements());
        response.put("totalPages", logs.getTotalPages());

        return ResponseEntity.ok(response);
    }
}