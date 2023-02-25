package com.school.grade.entities.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SchoolDisciplineRequestDTO {

    private String disciplineName;
    private int workload;
}
