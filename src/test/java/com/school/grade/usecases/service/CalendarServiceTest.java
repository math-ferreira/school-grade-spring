/*
package com.school.grade.usecases.service;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.calendar.DateDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.usecases.service.impl.CalendarServiceImpl;
import com.school.grade.utils.mock.GradeRequestMock;
import com.school.grade.web.exception.handler.ElementNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.school.grade.entities.enums.StatusEnum.NOT_AVAILABLE;
import static com.school.grade.utils.mock.GradeRequestMock.buildCalendarDTO;
import static com.school.grade.utils.mock.GradeRequestMock.createScheduleDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CalendarServiceTest {

    @InjectMocks
    private CalendarServiceImpl calendarService;

    private final GradeRequestDTO gradeRequestMock = GradeRequestMock.createGradeRequestSampleOne();


    @DisplayName("should initialize calendar with success")
    @Test
    void initializeCalendarWithSuccess() {

        CalendarDTO calendarDTO = calendarService.initializeCalendar(gradeRequestMock);

        assertThat(calendarDTO.getDateList()).isNotEmpty();

        assertThat(calendarDTO.getDateList().get(0).getDate())
                .isAfterOrEqualTo(gradeRequestMock.getSchoolData().getBeginningSemester());

        assertThat(calendarDTO.getDateList().get(calendarDTO.getDateList().size() - 1).getDate())
                .isBeforeOrEqualTo(gradeRequestMock.getSchoolData().getEndingSemester());

    }

    @DisplayName("should override calendar with success")
    @Test
    void overrideCalendarWithSuccess() {

        CalendarDTO calendar = buildCalendarDTO();
        List<ScheduleDTO> schedule = createScheduleDTO();

        CalendarDTO calendarDTO = calendarService.overrideCalendar(calendar, schedule);

        DateDTO firstDate = assertDoesNotThrow(() -> CalendarService.getDateDTOByDay(calendarDTO, LocalDate.of(2023, Month.JANUARY, 1)));
        DateDTO secondDate = assertDoesNotThrow(() -> CalendarService.getDateDTOByDay(calendarDTO, LocalDate.of(2023, Month.JANUARY, 3)));
        DateDTO thirdDate = assertDoesNotThrow(() -> CalendarService.getDateDTOByDay(calendarDTO, LocalDate.of(2023, Month.JANUARY, 6)));
        DateDTO fourthDate = assertDoesNotThrow(() -> CalendarService.getDateDTOByDay(calendarDTO, LocalDate.of(2023, Month.JANUARY, 9)));

        assertThat(firstDate.getStatus())
                .isEqualTo(NOT_AVAILABLE);

        assertThat(secondDate.getStatus())
                .isEqualTo(NOT_AVAILABLE);

        assertThat(thirdDate.getStatus())
                .isEqualTo(NOT_AVAILABLE);

        assertThat(fourthDate.getStatus())
                .isEqualTo(NOT_AVAILABLE);

    }

    @DisplayName("should throw exception when there is no date dto available in calendar")
    @Test
    void throwExceptionWhenThereIsNoMatchDay() {

        CalendarDTO calendar = buildCalendarDTO();
        List<ScheduleDTO> schedule = createScheduleDTO();

        CalendarDTO calendarDTO = calendarService.overrideCalendar(calendar, schedule);

        assertThrows(
                ElementNotFoundException.class,
                () -> CalendarService.getDateDTOByDay(calendarDTO, LocalDate.of(1995, Month.APRIL, 23)));

    }

}
*/
