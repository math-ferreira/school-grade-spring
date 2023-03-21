package com.school.grade.entities.dto.calendar;

import com.school.grade.entities.enums.DateTypeEnum;
import com.school.grade.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DateDTO {
    private LocalDate date;
    private DateTypeEnum dateType;
    private StatusEnum status;

     public DateDTO(LocalDate date) {
         this.date = date;
     }

}
