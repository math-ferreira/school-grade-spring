package com.school.grade.utils.mock;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDatesRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDisciplineRequestDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class GradeRequestMock {

    public GradeRequestDTO createGradeRequest() {
        GradeRequestDTO gradeRequest = new GradeRequestDTO();
        gradeRequest.setDiscipline(buildDiscipline());
        gradeRequest.setHolidays(buildHoliday());
        gradeRequest.setSchoolDates(buildSchoolDates());

        return gradeRequest;
    }

    private SchoolDisciplineRequestDTO buildDiscipline() {
        return new SchoolDisciplineRequestDTO(
                "Desenho tecnico",
                24,
                2);
    }

    private SchoolDatesRequestDTO buildSchoolDates() {
        return new SchoolDatesRequestDTO(
                LocalDate.of(2023, Month.JANUARY, 1),
                LocalDate.of(2023, Month.JANUARY, 31),
                DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY
        );
    }

    private List<HolidayRequestDTO> buildHoliday() {
        return List.of(
                new HolidayRequestDTO("holiday 0101", LocalDate.of(2023, Month.JANUARY, 12)),
                new HolidayRequestDTO("holiday 0202", LocalDate.of(2023, Month.JANUARY, 20)),
                new HolidayRequestDTO("holiday 0303", LocalDate.of(2023, Month.JANUARY, 31))

        );
    }
}
