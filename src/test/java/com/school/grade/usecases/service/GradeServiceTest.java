package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.grade.DisciplineClassesResponseDTO;
import com.school.grade.entities.dto.grade.response.grade.builder.GradeResponseDTO;
import com.school.grade.usecases.service.impl.GradeServiceImpl;
import com.school.grade.utils.mock.GradeRequestMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GradeServiceTest {

    @InjectMocks
    private GradeServiceImpl gradeService;

    private final GradeRequestDTO gradeRequestMock = new GradeRequestMock().createGradeRequest();

    @DisplayName("remaining hours should be zero when workload generate an integer numbers of classes")
    @Test
    public void workloadWithoutRemainingTest() {
        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DisciplineClassesResponseDTO classesHoursByDiscipline = gradeResponseDTO.getDisciplineClasses();

        assertThat(gradeRequestMock.getDiscipline().getWorkload())
                .isEqualTo(24);

        assertThat(classesHoursByDiscipline.getTotalFullDays())
                .isEqualTo(8);

        assertThat(classesHoursByDiscipline.getRemainingHours())
                .isEqualTo(0);
    }

    @DisplayName("remaining hours should be different from zero when workload generate a decimal numbers of classes")
    @Test
    public void workloadWithRemainingTest() {

        gradeRequestMock.getDiscipline().setWorkload(29);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DisciplineClassesResponseDTO classesHoursByDiscipline = gradeResponseDTO.getDisciplineClasses();

        assertThat(gradeRequestMock.getDiscipline().getWorkload())
                .isEqualTo(29);

        assertThat(classesHoursByDiscipline.getTotalFullDays())
                .isEqualTo(9);

        assertThat(classesHoursByDiscipline.getRemainingHours())
                .isEqualTo(2);
    }


    @DisplayName("day of week to start discipline should be the same of beginning of semester")
    @Test
    public void sameDayOfWeekToStartDiscipline() {

        LocalDate customDate = LocalDate.of(2023, Month.JANUARY, 4);

        gradeRequestMock.getSchoolDates().setBeginningSemester(customDate);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();

        assertThat(customDate.getDayOfWeek())
                .isEqualTo(DayOfWeek.WEDNESDAY);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.WEDNESDAY);
    }

    @DisplayName("day of week to start discipline should be different of beginning of semester when its on weekend")
    @Test
    public void differentDayOfWeekToStartDiscipline() {

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();

        assertThat(gradeRequestMock.getSchoolDates().getBeginningSemester().getDayOfWeek())
                .isNotEqualTo(dayOfWeekToStartDiscipline);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.MONDAY);
    }

    @DisplayName("day of week to start discipline should be different same of beginning of semester when its on friday")
    @Test
    public void weekendDayOfWeekToStartDiscipline() {

        LocalDate customDate = LocalDate.of(2023, Month.JANUARY, 6);

        gradeRequestMock.getSchoolDates().setBeginningSemester(customDate);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();

        assertThat(gradeRequestMock.getSchoolDates().getBeginningSemester().getDayOfWeek())
                .isNotEqualTo(dayOfWeekToStartDiscipline);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.MONDAY);
    }


    @DisplayName("the second day of week for discipline should be within 2 days of the first class")
    @Test
    public void secondDayOfWeekForDiscipline() {

        LocalDate customDate = LocalDate.now();

        gradeRequestMock.getSchoolDates().setBeginningSemester(customDate);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();
        DayOfWeek secondDayOfWeekForDiscipline = gradeResponseDTO.getDaysOfWeek().getSecondDayOfWeek();

        DayOfWeek firstDayOfWeek = (dayOfWeekToStartDiscipline.getValue() < secondDayOfWeekForDiscipline.getValue())
                ? dayOfWeekToStartDiscipline : secondDayOfWeekForDiscipline;

        DayOfWeek secondDayOfWeek = (dayOfWeekToStartDiscipline.getValue() > secondDayOfWeekForDiscipline.getValue())
                ? dayOfWeekToStartDiscipline : secondDayOfWeekForDiscipline;

        assertThat(secondDayOfWeek)
                .isEqualTo(DayOfWeek.of(firstDayOfWeek.getValue()).plus(2));
    }

}
