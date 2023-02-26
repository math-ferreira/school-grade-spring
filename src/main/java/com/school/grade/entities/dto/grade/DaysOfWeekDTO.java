package com.school.grade.entities.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DaysOfWeekDTO {
    DayOfWeek firstDayOfWeek;
    DayOfWeek secondDayOfWeek;
    LocalDate disciplineStartDate;

}
