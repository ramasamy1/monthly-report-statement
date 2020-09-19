package com.monthly.statementprocessor.processor;

import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.processor.FileProcessor;
import com.monthly.statementprocessor.processor.XmlProcessor;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlProcessorTest {

    @Test
    public void processSuccess() {
        FileProcessor xmlProcessor = new XmlProcessor();
        StatementInput input = xmlProcessor.process(getClass().getResourceAsStream("/records.xml"));

        assertEquals(10, input.getInput().size());
        assertEquals(Long.valueOf("187997"), input.getInput().get(0).getReference());
        assertEquals("NL91RABO0315273637", input.getInput().get(0).getAccountNumber());
        assertEquals("Clothes for Rik King", input.getInput().get(0).getDescription());
        assertEquals(BigDecimal.valueOf(57.6), input.getInput().get(0).getStartBalance());
        assertEquals(BigDecimal.valueOf(-32.98), input.getInput().get(0).getMutation());
        assertEquals(BigDecimal.valueOf(24.62), input.getInput().get(0).getEndBalance());
    }

    @Test(expected = StatementProcessException.class)
    public void processFailureWrongData() {
        FileProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.process(getClass().getResourceAsStream("/wrong_records.xml"));
    }

    @Test(expected = StatementProcessException.class)
    public void processFailure() {
        FileProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.process(getClass().getResourceAsStream("/invalid.xml"));
    }
}
