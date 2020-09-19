package com.monthly.statementprocessor.controller;

import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementOutput;
import com.monthly.statementprocessor.service.StatementProcessorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("customer/api/monthly/")
public class StatementProcessorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatementProcessorController.class);

    private StatementProcessorService statementProcessorService;

    @Autowired
    public StatementProcessorController(StatementProcessorService statementProcessorService) {
        this.statementProcessorService = statementProcessorService;
    }

    @PostMapping("process-statement")
    public ResponseEntity<StatementOutput> handle(@NotNull @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(statementProcessorService.execute(file));
    }

    @ExceptionHandler(StatementProcessException.class)
    public ResponseEntity<String> handleStatementProcessException(RuntimeException re) {
        LOGGER.info("Exception in process", re);
        return ResponseEntity.badRequest().body("Exception in process: " + re.getMessage());
    }
}