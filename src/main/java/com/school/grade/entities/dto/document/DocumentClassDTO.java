package com.school.grade.entities.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DocumentClassDTO {
    private LocalDate dateOfClass;
    private DayOfWeek dawOfWeek;
    private String disciplineInitials;
    
}
