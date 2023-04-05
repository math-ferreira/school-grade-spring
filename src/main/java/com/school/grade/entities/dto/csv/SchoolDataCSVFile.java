package com.school.grade.entities.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.school.grade.entities.dto.csv.converter.LocalDateConverter;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SchoolDataCSVFile {

    @CsvBindByPosition(position = 0)
    private String semesterName;
    @CsvBindByPosition(position = 1)
    private String courseName;
    @CsvCustomBindByPosition(position = 2, converter = LocalDateConverter.class)
    private LocalDate beginningSemester;
    @CsvCustomBindByPosition(position = 3, converter = LocalDateConverter.class)
    private LocalDate endingSemester;
    @CsvCustomBindByPosition(position = 4, converter = LocalDateConverter.class)
    private LocalDate beginningVacation;
    @CsvCustomBindByPosition(position = 5, converter = LocalDateConverter.class)
    private LocalDate endingVacation;
    @CsvBindByPosition(position = 6)
    private String beginningDayOfWeek;
    @CsvBindByPosition(position = 7)
    private String endingDayOfWeek;

}
