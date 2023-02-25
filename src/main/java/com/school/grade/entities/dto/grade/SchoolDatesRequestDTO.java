package com.school.grade.entities.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SchoolDatesRequestDTO {

    private LocalDate beginningSemester;
    private LocalDate endSemester;
}
