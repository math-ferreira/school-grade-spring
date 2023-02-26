package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.DisciplineClassesDTO;
import com.school.grade.entities.dto.grade.ScheduleClassesDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDatesRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDisciplineRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.school.grade.entities.constants.GradeConstants.MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
import static java.time.DayOfWeek.*;

@Service
public class GradeServiceImpl implements GradeService {
    @Override
    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO) {

        DaysOfWeekDTO daysOfWeek = createDaysOfWeekForDiscipline(gradeRequestDTO);
        DisciplineClassesDTO disciplineClasses = createdDisciplineClasses(gradeRequestDTO.getDiscipline());
        List<ScheduleClassesDTO> scheduleClasses = createScheduleClasses(daysOfWeek, disciplineClasses);

        return new GradeResponseDTO.GradeBuilder()
                .setDaysOfWeek(daysOfWeek)
                .setDisciplineClasses(disciplineClasses)
                .setScheduleClasses(scheduleClasses)
                .build();

    }

    private DaysOfWeekDTO createDaysOfWeekForDiscipline(GradeRequestDTO gradeRequestDTO) {

        SchoolDatesRequestDTO schoolDatesRequest = gradeRequestDTO.getSchoolDates();

        DayOfWeek firstDayOfWeekForDiscipline = getNearestDayOfWeekToStartDiscipline(schoolDatesRequest);
        DayOfWeek secondDayOfWeekForDiscipline = getSecondDayOfWeekForDiscipline(firstDayOfWeekForDiscipline);
        LocalDate disciplineStartDate = getDisciplineStartDate(firstDayOfWeekForDiscipline, schoolDatesRequest.getBeginningSemester());

        return new DaysOfWeekDTO(firstDayOfWeekForDiscipline, secondDayOfWeekForDiscipline, disciplineStartDate);
    }

    private DisciplineClassesDTO createdDisciplineClasses(SchoolDisciplineRequestDTO discipline) {

        int totalFullDays = discipline.getWorkload() / MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
        int remainingHours = discipline.getWorkload() % MANDATORY_NUMBER_OF_CLASSES_PER_DAY;

        return new DisciplineClassesDTO(totalFullDays, remainingHours);
    }

    private List<ScheduleClassesDTO> createScheduleClasses(
            DaysOfWeekDTO daysOfWeek,
            DisciplineClassesDTO disciplineClasses) {

        LocalDate currentDate = daysOfWeek.getDisciplineStartDate();
        List<ScheduleClassesDTO> scheduleClasses = new ArrayList<>();

        for (int numberOfClass = 0; (numberOfClass < disciplineClasses.getTotalFullDays()); numberOfClass++) {
            scheduleClasses.add(
                    new ScheduleClassesDTO(numberOfClass + 1, currentDate, currentDate.getDayOfWeek())
            );
            DayOfWeek nextDayOfWeek = getNextDayOfWeek(currentDate, daysOfWeek);
            currentDate = getNextDateOfClass(currentDate, nextDayOfWeek);
        }

        return scheduleClasses;

    }

    private LocalDate getNextDateOfClass(LocalDate currentDate, DayOfWeek nextDayOfWeek) {
        while (currentDate.getDayOfWeek().getValue() != nextDayOfWeek.getValue()) {
            currentDate = currentDate.plusDays(1);
        }
        return currentDate;
    }

    private DayOfWeek getNextDayOfWeek(LocalDate currentDate, DaysOfWeekDTO daysOfWeek) {
        return (currentDate.getDayOfWeek().equals(daysOfWeek.getFirstDayOfWeek()))
                ? daysOfWeek.getSecondDayOfWeek() : daysOfWeek.getFirstDayOfWeek();
    }

    private LocalDate getDisciplineStartDate(
            DayOfWeek firstDayOfWeekForDiscipline,
            LocalDate beginningSemester
    ) {

        LocalDate currentDate = beginningSemester;

        while (currentDate.getDayOfWeek().getValue() != firstDayOfWeekForDiscipline.getValue()) {
            currentDate = currentDate.plusDays(1);
        }

        return currentDate;
    }


    private DayOfWeek getNearestDayOfWeekToStartDiscipline(SchoolDatesRequestDTO schoolDatesRequest) {

        DayOfWeek dayOfWeekToStartDiscipline = schoolDatesRequest.getBeginningSemester().getDayOfWeek();

        while (!isValidDayOfWeekToStartClasses(schoolDatesRequest, dayOfWeekToStartDiscipline)) {
            dayOfWeekToStartDiscipline = dayOfWeekToStartDiscipline.plus(1);
        }

        return dayOfWeekToStartDiscipline;
    }

    private boolean isValidDayOfWeekToStartClasses(
            SchoolDatesRequestDTO schoolDatesRequest,
            DayOfWeek currentDay) {

        DayOfWeek beginningDayOfWeek = schoolDatesRequest.getBeginningDayOfWeek();
        DayOfWeek endingDayOfWeek = schoolDatesRequest.getEndingDayOfWeek();

        return currentDay.getValue() >= beginningDayOfWeek.getValue() &&
                currentDay.getValue() <= endingDayOfWeek.getValue();
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
