package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.DisciplineScheduleResponseDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.CalendarService;
import com.school.grade.usecases.service.DisciplineService;
import com.school.grade.usecases.service.DocumentManagementService;
import com.school.grade.usecases.service.GradeService;
import com.school.grade.web.exception.handler.ElementNotFoundException;
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
    @Autowired
    private DocumentManagementService documentManagementService;

    @Override
    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO) {

        CalendarDTO calendarDTO = calendarSchoolService.initializeCalendar(gradeRequestDTO);

        List<DisciplineScheduleResponseDTO> gradeResponseList = new ArrayList<>();

        for (DisciplineRequestDTO schoolDisciplineRequest : gradeRequestDTO.getDisciplines()) {

            DisciplineScheduleResponseDTO.GradeBuilder gradeResponse = new DisciplineScheduleResponseDTO
                    .GradeBuilder()
                    .setDisciplineName(schoolDisciplineRequest.getDisciplineName())
                    .setDisciplineInitials(schoolDisciplineRequest.getDisciplineInitials())
                    .setTeacherName(schoolDisciplineRequest.getTeacherName())
                    .setPriorityOrder(schoolDisciplineRequest.getPriorityOrder())
                    .setWorkload(schoolDisciplineRequest.getWorkload());

            DaysAndHoursDTO daysAndHours = disciplineService.getDaysAndHours(schoolDisciplineRequest);
            gradeResponse.setDaysAndHours(daysAndHours);

            DaysOfWeekDTO daysOfWeek = createDaysOfWeekForDiscipline(calendarDTO, schoolDisciplineRequest.getTimesPerWeek());
            gradeResponse.setDaysOfWeek(daysOfWeek);

            List<ScheduleDTO> schedule = disciplineService.createScheduleForDiscipline(daysAndHours, daysOfWeek, calendarDTO);
            gradeResponse.setScheduleClasses(schedule);

            calendarDTO = calendarSchoolService.overrideCalendar(calendarDTO, schedule);

            gradeResponseList.add(gradeResponse.build());
        }

        GradeResponseDTO gradeResponseDTO =
                new GradeResponseDTO(
                        gradeRequestDTO.getSchoolData().getSemesterName(),
                        gradeRequestDTO.getSchoolData().getCourseName(),
                        gradeRequestDTO.getSchoolData().getBeginningVacation(),
                        gradeRequestDTO.getSchoolData().getEndingVacation(),
                        gradeResponseList,
                        gradeRequestDTO.getHolidays()
                );

        documentManagementService.createDocument(gradeResponseDTO);

        return gradeResponseDTO;
    }

    private DaysOfWeekDTO createDaysOfWeekForDiscipline(CalendarDTO calendarSchoolDTO, int timesPerWeek) {

        LocalDate disciplineStartDate = getDayToStartDiscipline(calendarSchoolDTO);
        DayOfWeek firstDayOfWeek = disciplineStartDate.getDayOfWeek();
        DayOfWeek secondDayOfWeek = (timesPerWeek == 1) ? firstDayOfWeek : getSecondDayOfWeekForDiscipline(firstDayOfWeek);

        return new DaysOfWeekDTO(firstDayOfWeek, secondDayOfWeek, disciplineStartDate);
    }

    private LocalDate getDayToStartDiscipline(CalendarDTO calendarSchoolDTO) {

        return calendarSchoolDTO.getDateList()
                .stream()
                .filter(schoolDate -> schoolDate.getStatus().equals(AVAILABLE))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("There are no days available for discipline"))
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




