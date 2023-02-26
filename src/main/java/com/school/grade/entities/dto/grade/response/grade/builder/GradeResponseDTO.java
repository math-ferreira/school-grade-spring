package com.school.grade.entities.dto.grade.response.grade.builder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.response.grade.DaysOfWeekResponseDTO;
import com.school.grade.entities.dto.grade.response.grade.DisciplineClassesResponseDTO;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class GradeResponseDTO {

    @JsonProperty
    private DisciplineClassesResponseDTO disciplineClasses;

    @JsonProperty
    private DaysOfWeekResponseDTO daysOfWeek;

    private GradeResponseDTO(GradeBuilder gradeBuilder) {
        this.disciplineClasses = gradeBuilder.disciplineClasses;
        this.daysOfWeek = gradeBuilder.daysOfWeek;

    }

    public static class GradeBuilder {
        private DisciplineClassesResponseDTO disciplineClasses;
        private DaysOfWeekResponseDTO daysOfWeek;

        public GradeBuilder setDisciplineClasses(DisciplineClassesResponseDTO disciplineClasses) {
            this.disciplineClasses = disciplineClasses;
            return this;
        }

        public GradeBuilder setDaysOfWeek(DaysOfWeekResponseDTO daysOfWeek) {
            this.daysOfWeek = daysOfWeek;
            return this;
        }

        public GradeResponseDTO build(){
            return new GradeResponseDTO(this);
        }
    }
}
