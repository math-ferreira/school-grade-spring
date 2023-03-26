package com.school.grade.entities.dto.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DocumentHolidayDTO {
    @JsonProperty
    private String holidayName;
    @JsonProperty
    private LocalDate holidayDate;
}
