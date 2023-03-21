package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
                .thenReturn(GradeRequestMock.overrideCalendarDTO());

        when(disciplineService.getDaysAndHours(any()))
                .thenReturn(GradeRequestMock.createDaysAndHoursDTO());

        when(disciplineService.createScheduleForDiscipline(any(), any(), any()))
                .thenReturn(GradeRequestMock.createScheduleDTO());
    }

    @DisplayName("should throw exception when there are not days available to schedule disciplines")
    @Test
    void throwExceptionWithoutDaysAvailable() {

        assertThrows(ElementNotFoundException.class, () -> gradeService.createGrade(new GradeRequestMock().createGradeRequestSampleTwo()));

    }

    @DisplayName("should create a grade with success")
    @Test
    void createGradeWithSuccess() {

        List<GradeResponseDTO> gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

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

/*

    @DisplayName("remaining hours should be different from zero when workload generate a decimal numbers of classes")
    @Test
    public void workloadWithRemainingTest() {

        gradeRequestMock.get(0).getDiscipline().setWorkload(29);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DisciplineClassesDTO classesHoursByDiscipline = gradeResponseDTO.getDisciplineClasses();

        assertThat(gradeRequestMock.get(0).getDiscipline().getWorkload())
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

        gradeRequestMock.get(0).getSchoolDates().setBeginningSemester(customDate);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();
        LocalDate disciplineStartDate = gradeResponseDTO.getDaysOfWeek().getDisciplineStartDate();

        assertThat(customDate.getDayOfWeek())
                .isEqualTo(DayOfWeek.WEDNESDAY);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.WEDNESDAY);

        assertThat(disciplineStartDate)
                .isEqualTo(customDate);
    }

    @DisplayName("day of week to start discipline should be different of beginning of semester when its on weekend")
    @Test
    public void differentDayOfWeekToStartDiscipline() {

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();
        LocalDate disciplineStartDate = gradeResponseDTO.getDaysOfWeek().getDisciplineStartDate();

        assertThat(gradeRequestMock.get(0).getSchoolDates().getBeginningSemester().getDayOfWeek())
                .isNotEqualTo(dayOfWeekToStartDiscipline);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.MONDAY);

        assertThat(disciplineStartDate)
                .isEqualTo(LocalDate.of(2023, Month.JANUARY, 2));

    }

    @DisplayName("day of week to start discipline should be different same of beginning of semester when its on friday")
    @Test
    public void weekendDayOfWeekToStartDiscipline() {

        LocalDate customDate = LocalDate.of(2023, Month.JANUARY, 6);

        gradeRequestMock.get(0).getSchoolDates().setBeginningSemester(customDate);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        DayOfWeek dayOfWeekToStartDiscipline = gradeResponseDTO.getDaysOfWeek().getFirstDayOfWeek();

        assertThat(gradeRequestMock.get(0).getSchoolDates().getBeginningSemester().getDayOfWeek())
                .isNotEqualTo(dayOfWeekToStartDiscipline);

        assertThat(dayOfWeekToStartDiscipline)
                .isEqualTo(DayOfWeek.MONDAY);
    }


    @DisplayName("the second day of week for discipline should be within 2 days of the first class")
    @Test
    public void secondDayOfWeekForDiscipline() {

        LocalDate customDate = LocalDate.now();

        gradeRequestMock.get(0).getSchoolDates().setBeginningSemester(customDate);

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

    @DisplayName("should get the schedule of classes with the correct list of dates")
    @Test
    public void correctScheduleClasses() {

        LocalDate customDate = LocalDate.of(2023, Month.JANUARY, 26);

        gradeRequestMock.get(0).getSchoolDates().setBeginningSemester(customDate);
        gradeRequestMock.get(0).getDiscipline().setWorkload(12);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        List<ScheduleClassesDTO> scheduleClasses = gradeResponseDTO.getScheduleClasses();

        assertThat(scheduleClasses).size().isEqualTo(4);

        List<ScheduleClassesDTO> correctList = List.of(
                new ScheduleClassesDTO(1, LocalDate.of(2023, Month.JANUARY, 26), DayOfWeek.THURSDAY),
                new ScheduleClassesDTO(2, LocalDate.of(2023, Month.JANUARY, 31), DayOfWeek.TUESDAY),
                new ScheduleClassesDTO(3, LocalDate.of(2023, Month.FEBRUARY, 2), DayOfWeek.THURSDAY),
                new ScheduleClassesDTO(4, LocalDate.of(2023, Month.FEBRUARY, 7), DayOfWeek.TUESDAY)
        );

        assertThat(scheduleClasses)
                .isEqualTo(correctList);

        assertThat(scheduleClasses.get(0).getDateOfClass())
                .isEqualTo(customDate);
    }

    @DisplayName("should get the schedule of classes with the correct list of dates even through months")
    @Test
    public void correctScheduleClassesThroughMonths() {

        gradeRequestMock.get(0).getDiscipline().setWorkload(58);

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        List<ScheduleClassesDTO> scheduleClasses = gradeResponseDTO.getScheduleClasses();

        assertThat(scheduleClasses).size().isEqualTo(19);

    }


    @DisplayName("should skip and postpone class when there is holiday in the same date")
    @Test
    public void skipPostponeClassWhenThereIsHoliday() {

        gradeRequestMock.get(0).getDiscipline().setWorkload(12);
        gradeRequestMock.get(0).setHolidays(
                List.of(
                        new HolidayRequestDTO("holiday one", LocalDate.of(2023, Month.JANUARY, 4)),
                        new HolidayRequestDTO("holiday two", LocalDate.of(2023, Month.JANUARY, 16)),
                        new HolidayRequestDTO("holiday three", LocalDate.of(2023, Month.JANUARY, 18))
                )
        );

        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        List<ScheduleClassesDTO> scheduleClasses = gradeResponseDTO.getScheduleClasses();

        assertThat(scheduleClasses).size().isEqualTo(4);

        List<ScheduleClassesDTO> correctList = List.of(
                new ScheduleClassesDTO(1, LocalDate.of(2023, Month.JANUARY, 2), DayOfWeek.MONDAY),
                new ScheduleClassesDTO(2, LocalDate.of(2023, Month.JANUARY, 9), DayOfWeek.MONDAY),
                new ScheduleClassesDTO(3, LocalDate.of(2023, Month.JANUARY, 11), DayOfWeek.WEDNESDAY),
                new ScheduleClassesDTO(4, LocalDate.of(2023, Month.JANUARY, 23), DayOfWeek.MONDAY)
        );

        assertThat(scheduleClasses)
                .isEqualTo(correctList);

    }

    @DisplayName("should create a grade simple bean when try to schedule successfully")
    @Test
    public void shouldCreateGradeSimpleBean() {
        GradeResponseDTO gradeResponseDTO = gradeService.createGrade(gradeRequestMock);

        runtimeBeanService.getAllBeans();

        verify(runtimeBeanService, times(1)).createOrLoadBean(gradeResponseDTO);
    }

*/

}
