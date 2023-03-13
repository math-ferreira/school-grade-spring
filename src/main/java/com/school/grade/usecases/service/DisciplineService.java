package com.school.grade.usecases.service;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;

import java.util.List;

public interface DisciplineService {

    DaysAndHoursDTO getDaysAndHours(DisciplineRequestDTO discipline);

    List<ScheduleDTO> createScheduleForDiscipline(
            DaysAndHoursDTO disciplineClasses,
            DaysOfWeekDTO daysOfWeek,
            CalendarDTO calendarDTO
    );
}
