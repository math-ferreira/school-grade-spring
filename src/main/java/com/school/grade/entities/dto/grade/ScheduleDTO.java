package com.school.grade.entities.dto.grade;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleDTO {
    private int numberOfClass;
    private LocalDate dateOfClass;
    private DayOfWeek dawOfWeek;
    
}
