package com.school.grade.entities.dto.grade.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class GradeRequestDTO {
    @JsonProperty
    private List<DisciplineRequestDTO> disciplines;
    @JsonProperty
    private SchoolDataRequestDTO schoolData;
    @JsonProperty
    private List<HolidayRequestDTO> holidays;
}
