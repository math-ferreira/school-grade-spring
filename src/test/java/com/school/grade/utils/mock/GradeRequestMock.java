package com.school.grade.utils.mock;

import com.school.grade.entities.dto.calendar.CalendarDTO;
import com.school.grade.entities.dto.calendar.DateDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import com.school.grade.entities.dto.grade.request.*;
import com.school.grade.entities.enums.DateTypeEnum;
import com.school.grade.entities.enums.StatusEnum;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class GradeRequestMock {

    public GradeRequestDTO createGradeRequest() {
        GradeRequestDTO gradeRequest = new GradeRequestDTO();
        gradeRequest.setDisciplines(buildDiscipline());
        gradeRequest.setHolidays(buildHoliday());
        gradeRequest.setSchoolData(buildSchoolData());
        return gradeRequest;
    }


    public static DaysAndHoursDTO createDaysAndHoursDTO() {
        return new DaysAndHoursDTO(12, 0);
    }

    public static List<ScheduleDTO> createScheduleDTOList() {
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
                        DayOfWeek.WEDNESDAY
                ),
                new ScheduleDTO(
                        4,
                        LocalDate.of(2023, Month.JANUARY, 9),
                        DayOfWeek.WEDNESDAY
                )
        );
    }

    public static CalendarDTO createCalendarDTO() {
        return new CalendarDTO(
                List.of(
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 1),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 2),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 3),
                                DateTypeEnum.HOLIDAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 4),
                                DateTypeEnum.HOLIDAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 5),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 6),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 7),
                                DateTypeEnum.OUT_OF_RANGE,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 8),
                                DateTypeEnum.OUT_OF_RANGE,
                                StatusEnum.NOT_AVAILABLE
                        )
                )
        );
    }

    public static CalendarDTO overrideCalendarDTO() {
        return new CalendarDTO(
                List.of(
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 1),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 2),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 3),
                                DateTypeEnum.HOLIDAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 4),
                                DateTypeEnum.HOLIDAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 5),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 6),
                                DateTypeEnum.BUSINESS_DAY,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 7),
                                DateTypeEnum.OUT_OF_RANGE,
                                StatusEnum.NOT_AVAILABLE
                        ),
                        new DateDTO(
                                LocalDate.of(2023, Month.JANUARY, 8),
                                DateTypeEnum.OUT_OF_RANGE,
                                StatusEnum.NOT_AVAILABLE
                        )
                )
        );
    }

    public static List<DisciplineRequestDTO> buildDiscipline() {
        return List.of(
                new DisciplineRequestDTO(
                        "Desenho tecnico",
                        24,
                        1),
                new DisciplineRequestDTO(
                        "Mecanica 1",
                        12,
                        2),
                new DisciplineRequestDTO(
                        "Algebra linear",
                        48,
                        3)
        );
    }

    private SchoolDataRequestDTO buildSchoolData() {
        return new SchoolDataRequestDTO(
                LocalDate.of(2023, Month.JANUARY, 1),
                LocalDate.of(2023, Month.JANUARY, 8),
                DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY
        );
    }

    private List<HolidayRequestDTO> buildHoliday() {
        return List.of(
                new HolidayRequestDTO("holiday 0101", LocalDate.of(2023, Month.JANUARY, 1)),
                new HolidayRequestDTO("holiday 0202", LocalDate.of(2023, Month.JANUARY, 3))
        );
    }
}
