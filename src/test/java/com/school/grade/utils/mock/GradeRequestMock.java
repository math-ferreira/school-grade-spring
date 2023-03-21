package com.school.grade.utils.mock;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.calendar.DateDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.DisciplineRequestDTO;
import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.request.HolidayRequestDTO;
import com.school.grade.entities.dto.grade.request.SchoolDataRequestDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.school.grade.entities.enums.DateTypeEnum.*;
import static com.school.grade.entities.enums.StatusEnum.AVAILABLE;
import static com.school.grade.entities.enums.StatusEnum.NOT_AVAILABLE;

public class GradeRequestMock {

    public static GradeRequestDTO createGradeRequestSampleOne() {
        GradeRequestDTO gradeRequest = new GradeRequestDTO();
        gradeRequest.setDisciplines(buildDiscipline());
        gradeRequest.setHolidays(buildHoliday());
        gradeRequest.setSchoolData(buildSchoolData());
        return gradeRequest;
    }

    public static GradeRequestDTO createGradeRequestSampleTwo() {
        GradeRequestDTO gradeRequest = new GradeRequestDTO();
        gradeRequest.setDisciplines(buildDisciplines());
        gradeRequest.setHolidays(buildHoliday());
        gradeRequest.setSchoolData(buildSchoolData());
        return gradeRequest;
    }


    public static DaysAndHoursDTO createDaysAndHoursDTO() {
        return new DaysAndHoursDTO(12, 0);
    }

    public static List<ScheduleDTO> createScheduleDTO() {
        return List.of(
                new ScheduleDTO(
                        1,
                        LocalDate.of(2023, Month.JANUARY, 1),
                        DayOfWeek.MONDAY
                ),
                new ScheduleDTO(
                        2,
                        LocalDate.of(2023, Month.JANUARY, 3),
                        DayOfWeek.WEDNESDAY
                ),
                new ScheduleDTO(
                        3,
                        LocalDate.of(2023, Month.JANUARY, 6),
                        DayOfWeek.MONDAY
                ),
                new ScheduleDTO(
                        4,
                        LocalDate.of(2023, Month.JANUARY, 9),
                        DayOfWeek.WEDNESDAY
                )
        );
    }

    public static CalendarDTO buildCalendarDTO() {
        List<DateDTO> dateList = new ArrayList<>();

        LocalDate date = LocalDate.of(2023, 3, 6).minusDays(160);

        for (int i = 0; date.isBefore(LocalDate.now()); i++) {

            if (i % 9 == 0 || i % 10 == 0) dateList.add(new DateDTO(date, OUT_OF_RANGE, NOT_AVAILABLE));
            else if (i % 4 == 0 && i % 6 == 0) dateList.add(new DateDTO(date, HOLIDAY, NOT_AVAILABLE));
            else dateList.add(new DateDTO(date, BUSINESS_DAY, AVAILABLE));

            date = date.plusDays(1);
        }
        return new CalendarDTO(dateList);
    }

    public static CalendarDTO overrideCalendarDTOOne() {
        return new CalendarDTO(
                List.of(
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 1),
                                BUSINESS_DAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 2),
                                BUSINESS_DAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 3),
                                HOLIDAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 4),
                                HOLIDAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 5),
                                BUSINESS_DAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 6),
                                BUSINESS_DAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 7),
                                OUT_OF_RANGE,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 8),
                                OUT_OF_RANGE,
                                NOT_AVAILABLE
                        )
                )
        );
    }

    public static CalendarDTO overrideCalendarDTOTwo() {
        return new CalendarDTO(
                List.of(
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 1),
                                BUSINESS_DAY,
                                NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 2),
                                BUSINESS_DAY,
                                AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 3),
                                BUSINESS_DAY,
                                AVAILABLE
                        )
                )
        );
    }

    public static List<DisciplineRequestDTO> buildDiscipline() {
        return List.of(
                new DisciplineRequestDTO(
                        "Desenho tecnico",
                        16,
                        1,
                        2)
        );
    }

    public static List<DisciplineRequestDTO> buildDisciplines() {
        return List.of(
                new DisciplineRequestDTO(
                        "Desenho tecnico",
                        2,
                        2,
                        2),
                new DisciplineRequestDTO(
                        "Mecanica de computadores",
                        3,
                        3,
                        2),
                new DisciplineRequestDTO(
                        "Comunicação Social",
                        3,
                        1,
                        2),
                new DisciplineRequestDTO(
                        "Algebra linear",
                        3,
                        4,
                        2)
        );
    }

    private static SchoolDataRequestDTO buildSchoolData() {
        return new SchoolDataRequestDTO(
                LocalDate.of(2023, Month.JANUARY, 1),
                LocalDate.of(2023, Month.MARCH, 28),
                DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY
        );
    }

    private static List<HolidayRequestDTO> buildHoliday() {
        return List.of(
                new HolidayRequestDTO("holiday 0101", LocalDate.of(2023, Month.JANUARY, 1)),
                new HolidayRequestDTO("holiday 0202", LocalDate.of(2023, Month.JANUARY, 3))
        );
    }
}
