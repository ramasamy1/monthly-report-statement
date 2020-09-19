package com.monthly.statementprocessor.processor;

import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.model.StatementRecord;
import com.monthly.statementprocessor.model.csv.CsvStatementRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CsvProcessor implements FileProcessor {
    @Override
    public StatementInput process(InputStream inputStream) {
        List<CsvStatementRecord> csvStatementRecords;
        try {
            csvStatementRecords = new CsvToBeanBuilder<CsvStatementRecord>(new BufferedReader(new InputStreamReader(inputStream)))
                    .withOrderedResults(false)
                    .withType(CsvStatementRecord.class)
                    .build()
                    .parse();
        } catch (NumberFormatException | IllegalStateException | NullPointerException exception) {
            throw new StatementProcessException("Invalid CSV file", exception);
        }
        return convert(csvStatementRecords);
    }

    private StatementInput convert(List<CsvStatementRecord> csvStatementRecords) {

        StatementInput statementInput = new StatementInput();
        statementInput.setInput(csvStatementRecords
                .stream()
                .map(this::mapCsvStatementRecord)
                .collect(Collectors.toList()));
        return statementInput;
    }

    private StatementRecord mapCsvStatementRecord(CsvStatementRecord record) {

        StatementRecord statementRecord = new StatementRecord();
        try {
            statementRecord.setReference(Long.parseLong(record.getReference()));
            statementRecord.setAccountNumber(record.getAccountNumber());
            statementRecord.setDescription(record.getDescription());
            statementRecord.setStartBalance(new BigDecimal(record.getStartBalance()));
            statementRecord.setMutation(new BigDecimal(record.getMutation()));
            statementRecord.setEndBalance(new BigDecimal(record.getEndBalance()));
        } catch (NumberFormatException nfe) {
            throw new StatementProcessException("Can't map csv data. Please check the input", nfe);
        }
        return statementRecord;
    }
}
