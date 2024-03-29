package com.school.grade.entities.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.school.grade.entities.dto.csv.converter.NumberConverter;
import lombok.Data;

@Data
public class DisciplineCSVFile {

    @CsvBindByPosition(position = 0)
    private String disciplineName;
    @CsvBindByPosition(position = 1)
    private String disciplineInitials;
    @CsvBindByPosition(position = 2)
    private String teacherName;
    @CsvCustomBindByPosition(position = 3, converter = NumberConverter.class)
    private int workload;
    @CsvCustomBindByPosition(position = 4, converter = NumberConverter.class)
    private int priorityOrder;
    @CsvCustomBindByPosition(position = 5, converter = NumberConverter.class)
    private int timesPerWeek;

}
