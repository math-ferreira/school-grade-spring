package com.school.grade.entities.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.school.grade.entities.dto.csv.converter.LocalDateConverter;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HolidayCSVFile {
    @CsvBindByPosition(position = 0)
    private String holidayName;
    @CsvCustomBindByPosition(position = 1, converter = LocalDateConverter.class)
    private LocalDate holidayDate;

}
