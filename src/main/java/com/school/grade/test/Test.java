package com.school.grade.test;

import com.school.grade.entities.dto.grade.SchoolDisciplineRequestDTO;
import com.school.grade.entities.dto.grade.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.SchoolDatesRequestDTO;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) {

        SchoolDisciplineRequestDTO grade = buildGrade();
        HolidayRequestDTO buildHoliday = buildHoliday();
        SchoolDatesRequestDTO buildSchool = buildSchool();

        float amountOfDays = grade.getWorkload() / 3;


    }


    private static SchoolDisciplineRequestDTO buildGrade() {
        return new SchoolDisciplineRequestDTO("Desenho tecnico", 24);
    }

    private static SchoolDatesRequestDTO buildSchool() {
        return new SchoolDatesRequestDTO(
                LocalDate.of(2023, Month.JANUARY, 1),
                LocalDate.of(2023, Month.JANUARY, 31)
        );
    }

    private static HolidayRequestDTO buildHoliday() {
        HashMap<String, LocalDate> holidays = new HashMap<>();
        holidays.put("holiday 0101", LocalDate.of(2023, Month.JANUARY, 12));
        holidays.put("holiday 0202", LocalDate.of(2023, Month.JANUARY, 20));
        holidays.put("holiday 0303", LocalDate.of(2023, Month.JANUARY, 31));

        return new HolidayRequestDTO(holidays);
    }
}
