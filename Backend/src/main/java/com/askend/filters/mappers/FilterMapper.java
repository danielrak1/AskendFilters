package com.askend.filters.mappers;

import com.askend.filters.DTO.FilterDTO;
import com.askend.filters.model.Filter;

import java.util.stream.Collectors;


public class FilterMapper {

    private FilterMapper () {}

    public static FilterDTO toDTO(Filter filter){
        FilterDTO dto = new FilterDTO();
        dto.setId(filter.getId());
        dto.setName(filter.getName());
        dto.setCriteriaList(
                filter.getCriteriaList().stream()
                        .map(criteria -> CriteriaMapper.toDTO(criteria))
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public static Filter toEntity(FilterDTO filterDTO){
        Filter filter =  new Filter();
        filter.setId(filterDTO.getId());
        filter.setName(filterDTO.getName());
        filter.setCriteriaList(
                filterDTO.getCriteriaList().stream()
                        .map(criteria -> CriteriaMapper.toEntity(criteria))
                        .collect(Collectors.toList())
        );
        return filter;
    }
}

