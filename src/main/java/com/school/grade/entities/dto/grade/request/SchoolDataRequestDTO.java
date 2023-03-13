package com.school.grade.entities.dto.grade.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.GradeSimpleBean;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@AllArgsConstructor
public class SchoolDataRequestDTO implements Comparable<GradeSimpleBean> {
    @JsonProperty
    private LocalDate beginningSemester;
    @JsonProperty
    private LocalDate endingSemester;
    @JsonProperty
    private DayOfWeek beginningDayOfWeek;
    @JsonProperty
    private DayOfWeek endingDayOfWeek;

    @Override
    public int compareTo(GradeSimpleBean o) {
        return 0;
    }
}
