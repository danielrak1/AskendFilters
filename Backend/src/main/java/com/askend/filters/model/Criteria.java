package com.askend.filters.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "criteria_items")
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CriteriaType type;

    @NotBlank
    @JsonProperty("criteria_condition")
    @Column(name = "criteria_condition")
    private String condition;

    @NotBlank
    @JsonProperty("criteria_value")
    @Column(name = "criteria_value")
    private String value;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;

}
