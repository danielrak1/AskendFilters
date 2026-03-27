package com.askend.filters.service;

import com.askend.filters.DTO.CriteriaDTO;
import com.askend.filters.DTO.FilterDTO;
import com.askend.filters.exception.FilterNotFoundException;
import com.askend.filters.model.Criteria;
import com.askend.filters.model.CriteriaType;
import com.askend.filters.model.Filter;
import com.askend.filters.repository.FilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

    @Mock
    private FilterRepository filterRepository;

    @InjectMocks
    private FilterService filterService;

    private Filter filter;
    private FilterDTO filterDTO;

    @BeforeEach
    void setUp() {
        Criteria criteria = new Criteria();
        criteria.setType(CriteriaType.AMOUNT);
        criteria.setCondition("GREATER_THAN");
        criteria.setValue("100");

        filter = new Filter();
        filter.setId(1L);
        filter.setName("Test Filter");
        filter.setCriteriaList(List.of(criteria));

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
    void getAllFilters_returnsAllFilters() {
        when(filterRepository.findAll()).thenReturn(List.of(filter));

        List<FilterDTO> result = filterService.getAllFilters();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Filter");
    }

    @Test
    void getFilterById_returnsFilter_whenFound() {
        when(filterRepository.findById(1L)).thenReturn(Optional.of(filter));

        FilterDTO result = filterService.getFilterById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Filter");
    }

    @Test
    void getFilterById_throws_whenNotFound() {
        when(filterRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> filterService.getFilterById(99L))
                .isInstanceOf(FilterNotFoundException.class);
    }

    @Test
    void saveFilter_setsCriteriaFilterReference() {
        when(filterRepository.save(any(Filter.class))).thenReturn(filter);

        FilterDTO result = filterService.saveFilter(filterDTO);

        assertThat(result.getName()).isEqualTo("Test Filter");
        assertThat(result.getCriteriaList()).hasSize(1);
    }

    @Test
    void deleteFilter_callsRepository() {
        when(filterRepository.findByIdWithLock(1L)).thenReturn(Optional.of(filter));
        filterService.deleteFilter(1L);

        verify(filterRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateFilter_updatesAndReturnsDTO() {
        // Repository first checks the filter exists, then saves the updated version
        when(filterRepository.findByIdWithLock(1L)).thenReturn(Optional.of(filter));
        when(filterRepository.save(any(Filter.class))).thenReturn(filter);

        FilterDTO result = filterService.updateFilter(1L, filterDTO);

        // Verify the result was mapped back to a DTO correctly
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Filter");
        verify(filterRepository, times(1)).save(any(Filter.class));
    }

    @Test
    void updateFilter_throws_whenNotFound() {
        when(filterRepository.findByIdWithLock(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> filterService.updateFilter(99L, filterDTO))
                .isInstanceOf(FilterNotFoundException.class);
    }
}