package com.askend.filters.service;

import com.askend.filters.DTO.FilterDTO;
import com.askend.filters.exception.FilterNotFoundException;
import com.askend.filters.mappers.FilterMapper;
import com.askend.filters.model.Filter;
import com.askend.filters.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FilterService {

    private final FilterRepository filterRepository;

    public List<FilterDTO> getAllFilters() {
        return filterRepository.findAll().stream()
                .map(filter -> FilterMapper.toDTO(filter))
                .toList();
    }

    public FilterDTO getFilterById(Long id) {
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new FilterNotFoundException(id));
        return FilterMapper.toDTO(filter);
    }

    @Transactional
    public FilterDTO saveFilter(FilterDTO filterDTO) {
        Filter filter = FilterMapper.toEntity(filterDTO);

        if (filter.getCriteriaList() != null) {
            filter.getCriteriaList().forEach(criteria -> criteria.setFilter(filter));
        }
        return FilterMapper.toDTO(filterRepository.save(filter));
    }

    @Transactional
    public FilterDTO updateFilter(Long id, FilterDTO filterDTO){
        filterRepository.findByIdWithLock(id).orElseThrow(() -> new FilterNotFoundException(id));
        Filter filter = FilterMapper.toEntity(filterDTO);
        filter.setId(id);
        if (filter.getCriteriaList() != null){
            filter.getCriteriaList().forEach(criteria -> criteria.setFilter(filter));
        }
        return FilterMapper.toDTO(filterRepository.save(filter));
    }

    @Transactional
    public void deleteFilter(Long id) {
        filterRepository.findByIdWithLock(id).orElseThrow(() -> new FilterNotFoundException(id));
        filterRepository.deleteById(id);
    }
}
