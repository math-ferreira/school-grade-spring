package com.school.grade.entities.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class HolidayRequestDTO {

    private Map<String, LocalDate> holiday;
}
