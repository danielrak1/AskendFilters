package com.askend.filters.controller;

import com.askend.filters.DTO.FilterDTO;

import com.askend.filters.service.FilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FilterController {
    private final FilterService filterService;

    @GetMapping
    public List<FilterDTO> getAllFilters() {
        return filterService.getAllFilters();
    }

    @GetMapping("/{id}")
    public FilterDTO getFilterById(@PathVariable Long id) {
        return filterService.getFilterById(id);
    }

    @PostMapping
    public FilterDTO createFilter(@Valid @RequestBody FilterDTO filterDTO) {
        return filterService.saveFilter(filterDTO);
    }

    @PutMapping("/{id}")
    public FilterDTO updateFilter(@PathVariable Long id, @Valid @RequestBody FilterDTO filterDTO) {
        return filterService.updateFilter(id, filterDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFilter(@PathVariable Long id) {
        filterService.deleteFilter(id);
    }
}
