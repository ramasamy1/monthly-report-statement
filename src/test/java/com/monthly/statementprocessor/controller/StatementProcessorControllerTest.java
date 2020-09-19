package com.monthly.statementprocessor.controller;

import com.monthly.statementprocessor.controller.StatementProcessorController;
import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.service.StatementProcessorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatementProcessorControllerTest {

    private InputStream is;
    private MockMvc mockMvc;

    @Mock
    private StatementProcessorService statementProcessorService;

    @Spy
    @InjectMocks
    private StatementProcessorController controller = new StatementProcessorController(statementProcessorService);

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        is = controller.getClass().getClassLoader().getResourceAsStream("/records.xml");
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/customer/api/monthly/process-statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void testUploadFileHttp404() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/process-statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(404)).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void testFailure() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", is);
        when(controller.handle(mockMultipartFile)).thenThrow(new StatementProcessException("Unexpected Exception"));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/customer/api/monthly/process-statement").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Exception in process: Unexpected Exception"));
    }
}
