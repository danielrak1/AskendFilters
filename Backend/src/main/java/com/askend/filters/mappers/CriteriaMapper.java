package com.askend.filters.mappers;

import com.askend.filters.DTO.CriteriaDTO;
import com.askend.filters.model.Criteria;

public class CriteriaMapper {
    private CriteriaMapper() {}
    public static CriteriaDTO toDTO(Criteria criteria) {
        CriteriaDTO dto = new CriteriaDTO();
        dto.setId(criteria.getId());
        dto.setType(criteria.getType());
        dto.setCondition(criteria.getCondition());
        dto.setValue(criteria.getValue());
        return dto;
    }

    public static Criteria toEntity(CriteriaDTO criteriaDTO) {
        Criteria criteria = new Criteria();
        criteria.setId(criteriaDTO.getId());
        criteria.setType(criteriaDTO.getType());
        criteria.setCondition(criteriaDTO.getCondition());
        criteria.setValue(criteriaDTO.getValue());
        return criteria;
    }
}
