package com.school.grade.entities.dto.grade.response.grade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisciplineClassesResponseDTO {
    private int totalFullDays;
    private int remainingHours;
    
}
