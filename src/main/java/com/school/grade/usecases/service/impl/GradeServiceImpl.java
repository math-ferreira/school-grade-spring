package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDatesRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDisciplineRequestDTO;
import com.school.grade.entities.dto.grade.response.grade.DaysOfWeekResponseDTO;
import com.school.grade.entities.dto.grade.response.grade.DisciplineClassesResponseDTO;
import com.school.grade.entities.dto.grade.response.grade.builder.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

import static com.school.grade.entities.constants.GradeConstants.MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
import static java.time.DayOfWeek.*;

@Service
public class GradeServiceImpl implements GradeService {
    @Override
    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO) {


        DaysOfWeekResponseDTO daysOfWeek = createDaysOfWeekForDiscipline(gradeRequestDTO);
        DisciplineClassesResponseDTO disciplineClasses = createdDisciplineClasses(gradeRequestDTO.getDiscipline());

        return new GradeResponseDTO
                .GradeBuilder()
                .setDaysOfWeek(daysOfWeek)
                .setDisciplineClasses(disciplineClasses)
                .build();

    }

    private DisciplineClassesResponseDTO createdDisciplineClasses(SchoolDisciplineRequestDTO discipline) {

        int totalFullDays = discipline.getWorkload() / MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
        int remainingHours = discipline.getWorkload() % MANDATORY_NUMBER_OF_CLASSES_PER_DAY;

        return new DisciplineClassesResponseDTO(totalFullDays, remainingHours);
    }


    private DaysOfWeekResponseDTO createDaysOfWeekForDiscipline(GradeRequestDTO gradeRequestDTO) {

        DayOfWeek firstDayOfWeekForDiscipline = getNearestDayOfWeekToStartDiscipline(gradeRequestDTO.getSchoolDates());
        DayOfWeek secondDayOfWeekForDiscipline = getSecondDayOfWeekForDiscipline(firstDayOfWeekForDiscipline);

        return new DaysOfWeekResponseDTO(firstDayOfWeekForDiscipline, secondDayOfWeekForDiscipline);
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
