package com.askend.filters.mappers;

import com.askend.filters.DTO.CriteriaDTO;
import com.askend.filters.DTO.FilterDTO;
import com.askend.filters.model.Criteria;
import com.askend.filters.model.CriteriaType;
import com.askend.filters.model.Filter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilterMapperTest {

    @Test
    void toDTO_mapsAllFieldsCorrectly() {
        Criteria criteria = new Criteria();
        criteria.setId(1L);
        criteria.setType(CriteriaType.AMOUNT);
        criteria.setCondition("GREATER_THAN");
        criteria.setValue("100");

        Filter filter = new Filter();
        filter.setId(1L);
        filter.setName("Test Filter");
        filter.setCriteriaList(List.of(criteria));

        FilterDTO dto = FilterMapper.toDTO(filter);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test Filter");
        assertThat(dto.getCriteriaList()).hasSize(1);
        assertThat(dto.getCriteriaList().get(0).getType()).isEqualTo(CriteriaType.AMOUNT);
        assertThat(dto.getCriteriaList().get(0).getCondition()).isEqualTo("GREATER_THAN");
        assertThat(dto.getCriteriaList().get(0).getValue()).isEqualTo("100");
    }

    @Test
    void toEntity_mapsAllFieldsCorrectly() {
        CriteriaDTO criteriaDTO = new CriteriaDTO();
        criteriaDTO.setId(1L);
        criteriaDTO.setType(CriteriaType.TITLE);
        criteriaDTO.setCondition("STARTS_WITH");
        criteriaDTO.setValue("Hello");

        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setId(1L);
        filterDTO.setName("Title Filter");
        filterDTO.setCriteriaList(List.of(criteriaDTO));

        Filter filter = FilterMapper.toEntity(filterDTO);

        assertThat(filter.getId()).isEqualTo(1L);
        assertThat(filter.getName()).isEqualTo("Title Filter");
        assertThat(filter.getCriteriaList()).hasSize(1);
        assertThat(filter.getCriteriaList().get(0).getType()).isEqualTo(CriteriaType.TITLE);
        assertThat(filter.getCriteriaList().get(0).getCondition()).isEqualTo("STARTS_WITH");
        assertThat(filter.getCriteriaList().get(0).getValue()).isEqualTo("Hello");
    }

    @Test
    void toDTO_thenToEntity_preservesAllFields() {
        Criteria criteria = new Criteria();
        criteria.setId(1L);
        criteria.setType(CriteriaType.DATE);
        criteria.setCondition("FROM");
        criteria.setValue("2024-01-01");

        Filter original = new Filter();
        original.setId(1L);
        original.setName("Round Trip Filter");
        original.setCriteriaList(List.of(criteria));

        Filter result = FilterMapper.toEntity(FilterMapper.toDTO(original));

        assertThat(result.getId()).isEqualTo(original.getId());
        assertThat(result.getName()).isEqualTo(original.getName());
        assertThat(result.getCriteriaList().get(0).getType())
                .isEqualTo(original.getCriteriaList().get(0).getType());
        assertThat(result.getCriteriaList().get(0).getCondition())
                .isEqualTo(original.getCriteriaList().get(0).getCondition());
        assertThat(result.getCriteriaList().get(0).getValue())
                .isEqualTo(original.getCriteriaList().get(0).getValue());
    }
}