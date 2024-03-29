package com.school.grade.usecases.service;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.calendar.DateDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.web.exception.handler.ElementNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

    CalendarDTO initializeCalendar(GradeRequestDTO gradeRequestDTO);
    CalendarDTO overrideCalendar(CalendarDTO calendarDTO, List<ScheduleDTO> classSchedule);

}
