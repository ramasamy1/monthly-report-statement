package com.monthly.statementprocessor.service;

import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementOutput;
import com.monthly.statementprocessor.service.StatementProcessorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatementProcessorServiceTest {

    @Mock
    MultipartFile multipartFile;
    private StatementProcessorService service = new StatementProcessorService();

    @Before
    public void init() {
        when(multipartFile.getContentType()).thenReturn("text/csv");
    }

    @Test
    public void executeSuccess() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/records.csv"));
        StatementOutput output = service.execute(multipartFile);
        assertEquals(3, output.getResult().size());
    }

    @Test(expected = StatementProcessException.class)
    public void executeFailure() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/invalid.csv"));
        service.execute(multipartFile);
    }
}
