package com.school.grade.entities.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ScheduleClassesDTO {
    private int numberOfClass;
    private LocalDate dateOfClass;
    private DayOfWeek dawOfWeek;
    
}
