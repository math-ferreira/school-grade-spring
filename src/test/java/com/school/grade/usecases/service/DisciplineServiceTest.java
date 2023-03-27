/*
package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.usecases.service.impl.DisciplineServiceImpl;
import com.school.grade.utils.mock.GradeRequestMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class DisciplineServiceTest {

    @InjectMocks
    private DisciplineServiceImpl disciplineServiceImpl;

    private final DisciplineRequestDTO disciplineRequestDTO = GradeRequestMock.buildDiscipline().get(0);

    @DisplayName("should get day and hours with success")
    @Test
    void shouldGetDayAndHoursWithSuccess() {

        DaysAndHoursDTO daysAndHoursDTO = disciplineServiceImpl.getDaysAndHours(disciplineRequestDTO);

        Assertions.assertThat(daysAndHoursDTO.getTotalFullDays()).isEqualTo(5);
        Assertions.assertThat(daysAndHoursDTO.getRemainingHours()).isEqualTo(1);

    }

    @DisplayName("should get day and hours without remaining Hours")
    @Test
    void shouldGetDayAndHoursWithoutRemainingHours() {

        disciplineRequestDTO.setWorkload(24);

        DaysAndHoursDTO daysAndHoursDTO = disciplineServiceImpl.getDaysAndHours(disciplineRequestDTO);

        Assertions.assertThat(daysAndHoursDTO.getTotalFullDays()).isEqualTo(8);
        Assertions.assertThat(daysAndHoursDTO.getRemainingHours()).isZero();

    }

    @DisplayName("should create schedule for discipline with success")
    @Test
    void shouldCreateScheduleForDisciplineWithSuccess() {

        DaysAndHoursDTO daysAndHoursDTO = disciplineServiceImpl.getDaysAndHours(disciplineRequestDTO);

        List<ScheduleDTO> schedule = disciplineServiceImpl.createScheduleForDiscipline(
                daysAndHoursDTO,
                new DaysOfWeekDTO(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, LocalDate.of(2023, 1, 1)),
                GradeRequestMock.buildCalendarDTO()
        );

        Set<DayOfWeek> days = schedule.stream().map(ScheduleDTO::getDawOfWeek).collect(Collectors.toSet());

        Assertions.assertThat(schedule).hasSize(5);
        Assertions.assertThat(days).containsExactly(DayOfWeek.MONDAY, DayOfWeek.THURSDAY);

    }

}
*/
