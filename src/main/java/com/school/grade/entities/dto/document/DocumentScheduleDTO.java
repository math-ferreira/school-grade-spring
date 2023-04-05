package com.school.grade.entities.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class DocumentScheduleDTO {
    private List<DocumentClassDTO> documentClassList;
    private List<DocumentHolidayDTO> documentHolidayList;
    private LocalDate beginningVacation;
    private LocalDate endingVacation;
    
}
