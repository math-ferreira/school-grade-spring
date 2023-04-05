package com.school.grade.entities.dto.calendar;

import com.school.grade.entities.enums.DateTypeEnum;
import com.school.grade.entities.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

import static com.school.grade.entities.enums.DateTypeEnum.BUSINESS_DAY;
import static com.school.grade.entities.enums.StatusEnum.AVAILABLE;

@Data
@AllArgsConstructor
public class DateDTO {
    private LocalDate date;
    private DateTypeEnum dateType;
    private StatusEnum status;

    public DateDTO(LocalDate date) {
        this.date = date;
        this.dateType = BUSINESS_DAY;
        this.status = AVAILABLE;
    }

    public void setDateType(DateTypeEnum dateType) {
        this.dateType = dateType;
        if (!dateType.equals(BUSINESS_DAY)) {
            this.status = StatusEnum.NOT_AVAILABLE;
        }
    }

}
