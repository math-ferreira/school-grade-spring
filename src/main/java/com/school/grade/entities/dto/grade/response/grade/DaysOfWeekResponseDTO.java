package com.school.grade.entities.dto.grade.response.grade;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
public class DaysOfWeekResponseDTO {

    DayOfWeek firstDayOfWeek;

    DayOfWeek secondDayOfWeek;

}
