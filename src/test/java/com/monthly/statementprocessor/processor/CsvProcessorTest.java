package com.monthly.statementprocessor.processor;

import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.processor.CsvProcessor;
import com.monthly.statementprocessor.processor.FileProcessor;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvProcessorTest {

    @Test
    public void processSuccess() {
        FileProcessor csvProcessor = new CsvProcessor();
        StatementInput input = csvProcessor.process(getClass().getResourceAsStream("/records.csv"));

        assertEquals(10, input.getInput().size());
    }

    @Test(expected = StatementProcessException.class)
    public void processFailureWrongData() {
        FileProcessor csvProcessor = new CsvProcessor();
        csvProcessor.process(getClass().getResourceAsStream("/wrong_records.csv"));
    }

    @Test(expected = StatementProcessException.class)
    public void processFailureWrongFile() {
        FileProcessor csvProcessor = new CsvProcessor();
        csvProcessor.process(getClass().getResourceAsStream("/invalid.csv"));
    }
}
