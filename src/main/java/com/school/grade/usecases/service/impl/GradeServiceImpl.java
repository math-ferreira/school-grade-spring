package com.school.grade.usecases.service.impl;

import com.school.grade.entities.GradeSimpleBean;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.DisciplineClassesDTO;
import com.school.grade.entities.dto.grade.ScheduleClassesDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDatesRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDisciplineRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import com.school.grade.usecases.service.RuntimeBeanService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.school.grade.entities.constants.GradeConstants.MANDATORY_NUMBER_OF_CLASSES_PER_DAY;
import static java.time.DayOfWeek.*;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private RuntimeBeanService runtimeBeanService;

    @Override
    public GradeResponseDTO createGrade(List<GradeRequestDTO> gradeRequestDTO) {

        GradeRequestDTO gradeRequestDTO2 = gradeRequestDTO.get(0);

        List<GradeResponseDTO> gradeResponseDTOList = gradeRequestDTO.stream()
                .map(this::createGradeForEachDiscipline)
                .toList();

        GradeResponseDTO gradeResponseDTO = createGradeForEachDiscipline(gradeRequestDTO2);

        return gradeResponseDTO;

    }

    private GradeResponseDTO createGradeForEachDiscipline(GradeRequestDTO gradeRequestDTO) {

        DaysOfWeekDTO daysOfWeek = createDaysOfWeekForDiscipline(gradeRequestDTO);
        DisciplineClassesDTO disciplineClasses = createdDisciplineClasses(gradeRequestDTO.getDiscipline());
        List<ScheduleClassesDTO> scheduleClasses = createScheduleClasses(
                daysOfWeek,
                disciplineClasses,
                gradeRequestDTO.getHolidays()
        );


        GradeResponseDTO gradeResponse = new GradeResponseDTO.GradeBuilder()
                .setDisciplineName(gradeRequestDTO.getDiscipline().getDisciplineName())
                .setPriorityOrder(gradeRequestDTO.getDiscipline().getPriorityOrder())
                .setDaysOfWeek(daysOfWeek)
                .setDisciplineClasses(disciplineClasses)
                .setScheduleClasses(scheduleClasses)
                .build();

        runtimeBeanService.createOrLoadBean(gradeResponse);

        return gradeResponse;
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
            DisciplineClassesDTO disciplineClasses,
            List<HolidayRequestDTO> holidayList
    ) {

        LocalDate currentDate = daysOfWeek.getDisciplineStartDate();
        List<ScheduleClassesDTO> scheduleClasses = new ArrayList<>();

        for (int numberOfClass = 0; (numberOfClass < disciplineClasses.getTotalFullDays()); ) {

            if (currentDateIsNotAHoliday(holidayList, currentDate)) {
                scheduleClasses.add(
                        new ScheduleClassesDTO(numberOfClass + 1, currentDate, currentDate.getDayOfWeek())
                );
                numberOfClass++;
            }

            DayOfWeek nextDayOfWeek = getNextDayOfWeek(currentDate, daysOfWeek);
            currentDate = getNextDateOfClass(currentDate, nextDayOfWeek);
        }

        return scheduleClasses;

    }

    private boolean currentDateIsNotAHoliday(List<HolidayRequestDTO> holidayList, LocalDate currentDate) {
        return holidayList.stream().noneMatch(holiday -> currentDate.equals(holiday.getHolidayDate()));
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
            LocalDate beginningDate
    ) {

        LocalDate currentDate = beginningDate;

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

    private DayOfWeek getComplementDayOfWeekForDiscipline(DayOfWeek dayOfWeekToStartDiscipline) {
        return switch (dayOfWeekToStartDiscipline) {
            case MONDAY -> TUESDAY;
            case TUESDAY -> WEDNESDAY;
            case WEDNESDAY -> THURSDAY;
            default -> MONDAY;
        };
    }

    private LocalDate getDateToStartNewDiscipline() {

        Pair<GradeSimpleBean, GradeSimpleBean> twoLastScheduleClasses = getTwoLastScheduleClasses();

        GradeSimpleBean penultimateClass = twoLastScheduleClasses.getKey();
        GradeSimpleBean lastClass = twoLastScheduleClasses.getValue();

        if (penultimateClass != null) {
            return penultimateClass.getLastClassDate().plusDays(1);
        } else {
            return lastClass.getLastClassDate().plusDays(1);
        }

    }

    private Pair<GradeSimpleBean, GradeSimpleBean> getTwoLastScheduleClasses() {

        Map<String, GradeSimpleBean> gradeSimpleBeanMap = runtimeBeanService.getAllBeans();

        List<GradeSimpleBean> gradeSimpleBeanList = gradeSimpleBeanMap.keySet().stream()
                .map(gradeSimpleBeanMap::get)
                .sorted()
                .toList();

        GradeSimpleBean penultimateClass = (gradeSimpleBeanList.size() > 1) ?
                gradeSimpleBeanList.get(gradeSimpleBeanList.size() - 2) : null;

        GradeSimpleBean lastClass = gradeSimpleBeanList.get(gradeSimpleBeanList.size() - 1);

        return Pair.of(penultimateClass, lastClass);

    }

}




