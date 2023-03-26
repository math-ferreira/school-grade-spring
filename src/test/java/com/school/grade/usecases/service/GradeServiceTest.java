package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.DisciplineScheduleResponseDTO;
import com.school.grade.usecases.service.impl.GradeServiceImpl;
import com.school.grade.utils.mock.GradeRequestMock;
import com.school.grade.web.exception.handler.ElementNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GradeServiceTest {

    @InjectMocks
    private GradeServiceImpl gradeService;

    @Mock
    private CalendarService calendarSchoolService;

    @Mock
    private DisciplineService disciplineService;

    private final GradeRequestDTO gradeRequestMock = GradeRequestMock.createGradeRequestSampleOne();

    @BeforeEach
    public void setup() {
        when(calendarSchoolService.initializeCalendar(any()))
                .thenReturn(GradeRequestMock.buildCalendarDTO());

        when(calendarSchoolService.overrideCalendar(any(), any()))
                .thenReturn(GradeRequestMock.overrideCalendarDTOOne());

        when(disciplineService.getDaysAndHours(any()))
                .thenReturn(GradeRequestMock.createDaysAndHoursDTO());

        when(disciplineService.createScheduleForDiscipline(any(), any(), any()))
                .thenReturn(GradeRequestMock.createScheduleDTO());
    }

    @DisplayName("should throw exception when there are no days available to schedule disciplines")
    @Test
    void throwExceptionWithoutDaysAvailable() {

        assertThrows(ElementNotFoundException.class, () -> gradeService.createGrade(GradeRequestMock.createGradeRequestSampleTwo()));

    }

    @DisplayName("should create a grade with success")
    @Test
    void createGradeWithSuccess() {

        List<DisciplineScheduleResponseDTO> gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        Assertions.assertThat(gradeResponseDTO)
                .hasSize(1);

        Assertions.assertThat(gradeResponseDTO.get(0).getScheduleClasses())
                .isNotEmpty();

        Assertions.assertThat(gradeResponseDTO.get(0).getDisciplineName())
                .isNotEmpty();

        Assertions.assertThat(gradeResponseDTO.get(0).getDaysAndHours())
                .isNotNull();

        Assertions.assertThat(gradeResponseDTO.get(0).getDaysOfWeek())
                .isNotNull();

    }

    @DisplayName("should create a grade of disciplines by each priority")
    @Test
    void createGradeOfDisciplinesByPriority() {

        when(calendarSchoolService.overrideCalendar(any(), any()))
                .thenReturn(GradeRequestMock.overrideCalendarDTOTwo());

        GradeRequestDTO customGradeRequest = GradeRequestMock.createGradeRequestSampleTwo();

        List<DisciplineScheduleResponseDTO> gradeResponseDTO = gradeService.createGrade(customGradeRequest);

        Assertions.assertThat(gradeResponseDTO).hasSize(4);

        Assertions.assertThat(gradeResponseDTO.get(0).getPriorityOrder()).isEqualTo(1);
        Assertions.assertThat(gradeResponseDTO.get(0).getDisciplineName()).isEqualTo("Comunicação Social");

        Assertions.assertThat(gradeResponseDTO.get(1).getPriorityOrder()).isEqualTo(2);
        Assertions.assertThat(gradeResponseDTO.get(1).getDisciplineName()).isEqualTo("Desenho tecnico");

        Assertions.assertThat(gradeResponseDTO.get(2).getPriorityOrder()).isEqualTo(3);
        Assertions.assertThat(gradeResponseDTO.get(2).getDisciplineName()).isEqualTo("Mecanica de computadores");

        Assertions.assertThat(gradeResponseDTO.get(3).getPriorityOrder()).isEqualTo(4);
        Assertions.assertThat(gradeResponseDTO.get(3).getDisciplineName()).isEqualTo("Algebra linear");

    }

    @DisplayName("should create a grade for discipline with one lesson per week")
    @Test
    void createGradeWithOneLesson() {

        gradeRequestMock.getDisciplines().get(0).setTimesPerWeek(1);

        gradeService.createGrade(gradeRequestMock);

        verify(disciplineService, times(1)).createScheduleForDiscipline(
                any(),
                eq(new DaysOfWeekDTO(DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY, LocalDate.of(2022, 9, 28))),
                any()
        );

    }

    @DisplayName("should create a grade for discipline with two lessons per week")
    @Test
    void createGradeWithTwoLessons() {

        gradeService.createGrade(gradeRequestMock);

        verify(disciplineService, times(1)).createScheduleForDiscipline(
                any(),
                eq(new DaysOfWeekDTO(DayOfWeek.WEDNESDAY, DayOfWeek.MONDAY, LocalDate.of(2022, 9, 28))),
                any()
        );
    }

}
