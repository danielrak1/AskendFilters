package com.askend.filters.controller;

import com.askend.filters.DTO.CriteriaDTO;
import com.askend.filters.DTO.FilterDTO;
import com.askend.filters.exception.FilterNotFoundException;
import com.askend.filters.exception.GlobalExceptionHandler;
import com.askend.filters.model.CriteriaType;
import com.askend.filters.service.FilterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class FilterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private FilterController filterController;

    @MockitoBean
    private FilterService filterService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private FilterDTO filterDTO;

    @BeforeEach
    void setUp() {

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(filterController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator).
                build();

        CriteriaDTO criteriaDTO = new CriteriaDTO();
        criteriaDTO.setType(CriteriaType.AMOUNT);
        criteriaDTO.setCondition("GREATER_THAN");
        criteriaDTO.setValue("100");

        filterDTO = new FilterDTO();
        filterDTO.setId(1L);
        filterDTO.setName("Test Filter");
        filterDTO.setCriteriaList(List.of(criteriaDTO));
    }

    @Test
    void getAllFilters_returnsOkWithFilters() throws Exception {
        when(filterService.getAllFilters()).thenReturn(List.of(filterDTO));

        mockMvc.perform(get("/api/filters"))
                .andExpect(status().isOk())
                // jsonPath drills into the JSON response to assert specific values
                .andExpect(jsonPath("$[0].name").value("Test Filter"))
                .andExpect(jsonPath("$[0].criteriaList[0].criteria_condition")
                        .value("GREATER_THAN"));
    }

    @Test
    void getFilterById_returnsOkWithFilter() throws Exception {
        when(filterService.getFilterById(1L)).thenReturn(filterDTO);

        mockMvc.perform(get("/api/filters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Filter"));
    }

    @Test
    void createFilter_returnsCreatedFilter() throws Exception {
        when(filterService.saveFilter(any(FilterDTO.class))).thenReturn(filterDTO);

        // Serialize the DTO to JSON and send it as the request body
        mockMvc.perform(post("/api/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Filter"));
    }

    @Test
    void createFilter_returnsBadRequest_whenNameIsBlank() throws Exception {
        FilterDTO invalid = new FilterDTO();
        invalid.setName("");               // Violates @NotBlank
        invalid.setCriteriaList(List.of()); // Violates @NotNull... actually @NotNull allows empty list

        mockMvc.perform(post("/api/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest()); // 400, not 200
    }

    @Test
    void updateFilter_returnsUpdatedFilter() throws Exception {
        when(filterService.updateFilter(eq(1L), any(FilterDTO.class))).thenReturn(filterDTO);

        mockMvc.perform(put("/api/filters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Filter"));
    }

    @Test
    void deleteFilter_returnsOk() throws Exception {
        doNothing().when(filterService).deleteFilter(1L);

        mockMvc.perform(delete("/api/filters/1"))
                .andExpect(status().isOk());

        // Verify the service method was actually called once
        verify(filterService, times(1)).deleteFilter(1L);
    }

    @Test
    void getFilterById_returnsNotFound_whenMissing() throws Exception {
        when(filterService.getFilterById(99L))
                .thenThrow(new FilterNotFoundException(99L));

        mockMvc.perform(get("/api/filters/99"))
                .andExpect(status().isNotFound());
    }
}