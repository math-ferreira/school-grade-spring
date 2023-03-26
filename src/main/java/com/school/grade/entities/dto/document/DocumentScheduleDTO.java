package com.school.grade.entities.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentScheduleDTO {
    private List<DocumentClassDTO> documentClassList;
    private List<DocumentHolidayDTO> documentHolidayList;
    
}
