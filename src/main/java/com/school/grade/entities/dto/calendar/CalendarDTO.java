package com.school.grade.entities.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalendarDTO {

    private List<DateDTO> dateList;

}
