package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.usecases.service.SubjectService;
import com.school.grade.web.exception.handler.ElementNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.school.grade.entities.constants.GradeConstants.MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
import static com.school.grade.entities.enums.StatusEnum.AVAILABLE;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Override
    public DaysAndHoursDTO getDaysAndHours(DisciplineRequestDTO discipline) {

        int totalFullDays = discipline.getWorkload() / MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
        int remainingHours = discipline.getWorkload() % MANDATORY_NUMBER_OF_CLASSES_PER_DAY;

        return new DaysAndHoursDTO(totalFullDays, remainingHours);
    }

    @Override
    public List<ScheduleDTO> createScheduleForSubject(
            DaysAndHoursDTO disciplineClasses,
            DaysOfWeekDTO daysOfWeek,
            CalendarDTO calendarDTO
    ) {

        LocalDate currentDate = daysOfWeek.getDisciplineStartDate();
        List<ScheduleDTO> scheduleClasses = new ArrayList<>();

        for (int numberOfClass = 0; (numberOfClass < disciplineClasses.getTotalFullDays()); ) {

            if (isDateAvailableInCalendar(calendarDTO, currentDate)) {
                scheduleClasses.add(
                        new ScheduleDTO(numberOfClass + 1, currentDate, currentDate.getDayOfWeek())
                );
                numberOfClass++;
            }

            DayOfWeek nextDayOfWeek = getNextDayOfWeek(currentDate, daysOfWeek);
            currentDate = getNextDateOfClass(currentDate, nextDayOfWeek);
        }

        return scheduleClasses;

    }

    private boolean isDateAvailableInCalendar(CalendarDTO calendarDTO, LocalDate currentDate) {
        return calendarDTO.getDateList()
                .stream()
                .filter(date -> date.getDate().equals(currentDate))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("There are no days available in calendar"))
                .getStatus()
                .equals(AVAILABLE);
    }

    private DayOfWeek getNextDayOfWeek(LocalDate currentDate, DaysOfWeekDTO daysOfWeek) {
        return (currentDate.getDayOfWeek().equals(daysOfWeek.getFirstDayOfWeek()))
                ? daysOfWeek.getSecondDayOfWeek() : daysOfWeek.getFirstDayOfWeek();
    }

    private LocalDate getNextDateOfClass(LocalDate currentDate, DayOfWeek nextDayOfWeek) {
        if (currentDate.getDayOfWeek().equals(nextDayOfWeek))
            currentDate = currentDate.plusDays(1);

        while (currentDate.getDayOfWeek().getValue() != nextDayOfWeek.getValue()) {
            currentDate = currentDate.plusDays(1);
        }
        return currentDate;
    }

}
