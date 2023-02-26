package com.school.grade.entities.dto.grade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.DisciplineScheduleDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class GradeResponseDTO {

    @JsonProperty
    private DisciplineScheduleDetails classesHoursByDiscipline;


    public static GradeResponseDTO create(
            DisciplineScheduleDetails classesHoursByDiscipline
    ) {
        return new GradeResponseDTO(
                classesHoursByDiscipline
        );
    }
}
