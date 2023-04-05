package com.school.grade.entities.dto.grade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class GradeResponseDTO {

    @JsonProperty
    private String semesterName;
    @JsonProperty
    private String courseName;
    @JsonProperty
    private LocalDate beginningVacation;
    @JsonProperty
    private LocalDate endingVacation;
    @JsonProperty
    private List<DisciplineScheduleResponseDTO> disciplineSchedule;
    @JsonProperty
    private List<HolidayRequestDTO> holidays;
}
