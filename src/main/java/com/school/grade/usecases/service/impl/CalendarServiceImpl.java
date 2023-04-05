package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.calendar.DateDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDataRequestDTO;
import com.school.grade.usecases.service.CalendarService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.school.grade.entities.enums.DateTypeEnum.*;
import static com.school.grade.entities.enums.StatusEnum.NOT_AVAILABLE;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Override
    public CalendarDTO initializeCalendar(GradeRequestDTO gradeRequestDTO) {

        LocalDate currentlyDate = gradeRequestDTO.getSchoolData().getBeginningSemester();
        LocalDate endingDate = gradeRequestDTO.getSchoolData().getEndingSemester();

        List<DateDTO> calendarSchoolDTO = new ArrayList<>();

        while (currentlyDate.isBefore(endingDate)) {

            DateDTO schoolDateDTO = new DateDTO(currentlyDate);

            if (isHoliday(gradeRequestDTO.getHolidays(), currentlyDate)) {
                schoolDateDTO.setDateType(HOLIDAY);
            } else if (isVacation(gradeRequestDTO.getSchoolData(), currentlyDate)) {
                schoolDateDTO.setDateType(VACATION);
            } else if (isInvalidDayOfWeek(gradeRequestDTO.getSchoolData(), currentlyDate.getDayOfWeek())) {
                schoolDateDTO.setDateType(OUT_OF_RANGE);
            }

            calendarSchoolDTO.add(schoolDateDTO);

            currentlyDate = currentlyDate.plusDays(1);
        }

        return new CalendarDTO(calendarSchoolDTO);
    }

    @Override
    public CalendarDTO overrideCalendar(CalendarDTO calendarDTO, List<ScheduleDTO> classSchedule) {

        for (ScheduleDTO scheduleDTO : classSchedule) {
            LocalDate dateOfClass = scheduleDTO.getDateOfClass();

            for (int j = 0; j < calendarDTO.getDateList().size(); j++) {
                if (calendarDTO.getDateList().get(j).getDate().equals(dateOfClass)) {
                    calendarDTO.getDateList().get(j).setStatus(NOT_AVAILABLE);
                    break;
                }
            }
        }

        return calendarDTO;
    }

    private boolean isHoliday(List<HolidayRequestDTO> holidayList, LocalDate currentDate) {
        return holidayList.stream().anyMatch(holiday -> currentDate.equals(holiday.getHolidayDate()));
    }

    private boolean isVacation(SchoolDataRequestDTO schoolDatesRequest, LocalDate currentDate) {
        return currentDate.isEqual(schoolDatesRequest.getBeginningVacation()) || currentDate.isEqual(schoolDatesRequest.getEndingVacation())
                || (currentDate.isAfter(schoolDatesRequest.getBeginningVacation()) && currentDate.isBefore(schoolDatesRequest.getEndingVacation()));
    }

    private boolean isInvalidDayOfWeek(SchoolDataRequestDTO schoolDatesRequest, DayOfWeek currentDayOfWeek) {

        DayOfWeek beginningDayOfWeek = schoolDatesRequest.getBeginningDayOfWeek();
        DayOfWeek endingDayOfWeek = schoolDatesRequest.getEndingDayOfWeek();

        boolean currentDayOfWeekIsInRange = currentDayOfWeek.getValue() >= beginningDayOfWeek.getValue() &&
                currentDayOfWeek.getValue() <= endingDayOfWeek.getValue();

        return !currentDayOfWeekIsInRange;
    }
}
