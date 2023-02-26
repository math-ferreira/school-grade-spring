package com.school.grade.entities.dto.grade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.DayOfWeek;

@AllArgsConstructor
@Getter
@Setter
@JsonNaming(SnakeCaseStrategy.class)
public class DisciplineScheduleDetails {

    @JsonProperty
    private int totalFullDays;
    @JsonProperty
    private int remainingHours;

    @JsonProperty
    DayOfWeek firstDayOfWeek;

    @JsonProperty
    DayOfWeek secondDayOfWeek;
}
