package com.monthly.statementprocessor.validator;

import org.junit.Test;

import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.model.StatementRecord;
import com.monthly.statementprocessor.validator.StatementValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatementValidatorTest {

    @Test
    public void testValidate() {

        StatementInput input = new StatementInput();

        StatementRecord record1 = new StatementRecord();
        record1.setReference(Long.parseLong("154270"));
        record1.setAccountNumber("NL56RABO0149876948");
        record1.setDescription("Candy for Peter de Vries");
        record1.setStartBalance(new BigDecimal("5429"));
        record1.setMutation(new BigDecimal("-939"));
        record1.setEndBalance(new BigDecimal("6368"));

        StatementRecord record2 = new StatementRecord();
        record2.setReference(Long.parseLong("154270"));
        record2.setAccountNumber("NL56RABO0149456748");
        record2.setDescription("Candy for Siddhartha Mukherjee");
        record2.setStartBalance(new BigDecimal("100"));
        record2.setMutation(new BigDecimal("-50"));
        record2.setEndBalance(new BigDecimal("50"));

        StatementRecord record3 = new StatementRecord();
        record3.setReference(Long.parseLong("876543"));
        record3.setAccountNumber("NL56RABO0146543948");
        record3.setDescription("Something for someone");
        record3.setStartBalance(new BigDecimal("500"));
        record3.setMutation(new BigDecimal("-150"));
        record3.setEndBalance(new BigDecimal("400"));

        StatementRecord record4 = new StatementRecord();
        record3.setReference(Long.parseLong("876593"));
        record3.setAccountNumber("NL56RABO0146543948");
        record3.setDescription("Anything for anyone");
        record3.setStartBalance(new BigDecimal("500"));
        record3.setMutation(new BigDecimal("-150"));
        record3.setEndBalance(new BigDecimal("450"));

        List<StatementRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);

        input.setInput(recordList);

        assertEquals(3, StatementValidator.validate(input).size());
    }
}
