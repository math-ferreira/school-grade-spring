package com.school.grade.entities.dto.grade.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequestDTO {
    @JsonProperty
    private SchoolDataRequestDTO schoolData;
    @JsonProperty
    private List<DisciplineRequestDTO> disciplines;
    @JsonProperty
    private List<HolidayRequestDTO> holidays;

    public List<DisciplineRequestDTO> getDisciplines() {
        return this.disciplines.stream().sorted().toList();
    }
}
