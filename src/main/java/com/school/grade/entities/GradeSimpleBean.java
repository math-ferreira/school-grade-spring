package com.school.grade.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GradeSimpleBean {

    private LocalDate lastClassDate;
    private int priorityOrder;
    private String disciplineInitials;

}
