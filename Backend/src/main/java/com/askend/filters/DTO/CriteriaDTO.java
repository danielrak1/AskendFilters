package com.askend.filters.DTO;

import com.askend.filters.model.CriteriaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriteriaDTO {
    private Long id;

    private CriteriaType type;

    @JsonProperty("criteria_condition")
    private String condition;

    @JsonProperty("criteria_value")
    private String value;
}
