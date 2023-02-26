package com.school.grade.entities.dto.grade.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@AllArgsConstructor
public class HolidayRequestDTO {
    @JsonProperty
    private String holidayName;
    @JsonProperty
    private LocalDate holidayDate;
}
