package com.askend.filters.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private List<CriteriaDTO> criteriaList;
}
