package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.CalendarService;
import com.school.grade.usecases.service.DisciplineService;
import com.school.grade.usecases.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.school.grade.entities.enums.StatusEnum.AVAILABLE;
import static java.time.DayOfWeek.*;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private CalendarService calendarSchoolService;

    @Autowired
    private DisciplineService disciplineService;

    @Override
    public List<GradeResponseDTO> createGrade(GradeRequestDTO gradeRequestDTO) {

        CalendarDTO calendarDTO = calendarSchoolService.initializeCalendar(gradeRequestDTO);

        List<GradeResponseDTO> gradeResponseList = new ArrayList<>();

        for (DisciplineRequestDTO schoolDisciplineRequest : gradeRequestDTO.getDisciplines()) {

            GradeResponseDTO.GradeBuilder gradeResponse = new GradeResponseDTO
                    .GradeBuilder()
                    .setDisciplineName(schoolDisciplineRequest.getDisciplineName())
                    .setPriorityOrder(schoolDisciplineRequest.getPriorityOrder());

            DaysAndHoursDTO daysAndHours = disciplineService.getDaysAndHours(schoolDisciplineRequest);
            gradeResponse.setDaysAndHours(daysAndHours);

            DaysOfWeekDTO daysOfWeek = createDaysOfWeekForDiscipline(calendarDTO);
            gradeResponse.setDaysOfWeek(daysOfWeek);

            List<ScheduleDTO> schedule = disciplineService.createScheduleForDiscipline(daysAndHours, daysOfWeek, calendarDTO);
            gradeResponse.setScheduleClasses(schedule);

            calendarDTO = calendarSchoolService.overrideCalendar(calendarDTO, schedule);

            gradeResponseList.add(gradeResponse.build());
        }


        return gradeResponseList;
    }

    private DaysOfWeekDTO createDaysOfWeekForDiscipline(CalendarDTO calendarSchoolDTO) {

        LocalDate disciplineStartDate = getDayToStartDiscipline(calendarSchoolDTO);
        DayOfWeek firstDayOfWeek = disciplineStartDate.getDayOfWeek();
        DayOfWeek secondDayOfWeek = getSecondDayOfWeekForDiscipline(firstDayOfWeek);

        return new DaysOfWeekDTO(firstDayOfWeek, secondDayOfWeek, disciplineStartDate);
    }

    private LocalDate getDayToStartDiscipline(CalendarDTO calendarSchoolDTO) {

        return calendarSchoolDTO.getDateList()
                .stream()
                .filter(schoolDate -> schoolDate.getStatus().equals(AVAILABLE))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There are no days available"))
                .getDate();
    }

    private DayOfWeek getSecondDayOfWeekForDiscipline(DayOfWeek dayOfWeekToStartDiscipline) {
        return switch (dayOfWeekToStartDiscipline) {
            case MONDAY -> WEDNESDAY;
            case TUESDAY -> THURSDAY;
            case WEDNESDAY -> MONDAY;
            default -> TUESDAY;
        };
    }

}




