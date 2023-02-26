package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.grade.DisciplineScheduleDetails;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDatesRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

import static com.school.grade.entities.constants.GradeConstants.*;
import static java.time.DayOfWeek.*;

@Service
public class GradeServiceImpl implements GradeService {
    @Override
    public GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO) {

        DisciplineScheduleDetails disciplineDetails = buildDisciplineScheduleDetails(gradeRequestDTO);
        return GradeResponseDTO.create(disciplineDetails);

    }

    private DisciplineScheduleDetails buildDisciplineScheduleDetails(GradeRequestDTO gradeRequestDTO) {

        int totalFullDays = getDisciplineTotalFullDays(gradeRequestDTO.getDiscipline().getWorkload());
        int remainingHours = getDisciplineRemainingHours(gradeRequestDTO.getDiscipline().getWorkload());

        DayOfWeek firstDayOfWeekForDiscipline = getNearestDayOfWeekToStartDiscipline(gradeRequestDTO.getSchoolDates());
        DayOfWeek secondDayOfWeekForDiscipline = getSecondDayOfWeekForDiscipline(firstDayOfWeekForDiscipline);

        return new DisciplineScheduleDetails(
                totalFullDays,
                remainingHours,
                firstDayOfWeekForDiscipline,
                secondDayOfWeekForDiscipline
        );
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

    private int getDisciplineTotalFullDays(int workload) {
        return workload / MANDATORY_NUMBER_OF_CLASSES_PER_DAY ;
    }

    private int getDisciplineRemainingHours(int workload) {
        return workload % MANDATORY_NUMBER_OF_CLASSES_PER_DAY ;
    }
}
