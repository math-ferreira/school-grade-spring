package com.school.grade.entities.dto.grade.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class SchoolDisciplineRequestDTO {
    @JsonProperty
    private String disciplineName;
    @JsonProperty
    private int workload;

    @JsonProperty
    private int priorityOrder;
}
