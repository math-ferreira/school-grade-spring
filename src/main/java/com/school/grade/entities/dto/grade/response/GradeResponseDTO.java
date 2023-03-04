package com.school.grade.entities.dto.grade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.DisciplineClassesDTO;
import com.school.grade.entities.dto.grade.ScheduleClassesDTO;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class GradeResponseDTO {

    @JsonProperty
    private String disciplineName;

    @JsonProperty
    private DisciplineClassesDTO disciplineClasses;

    @JsonProperty
    private DaysOfWeekDTO daysOfWeek;

    @JsonProperty
    private List<ScheduleClassesDTO> scheduleClasses;
    private int priorityOrder;

    private GradeResponseDTO(GradeBuilder gradeBuilder) {
        this.disciplineClasses = gradeBuilder.disciplineClasses;
        this.daysOfWeek = gradeBuilder.daysOfWeek;
        this.scheduleClasses = gradeBuilder.scheduleClasses;
        this.disciplineName = gradeBuilder.disciplineName;
    }

    public static class GradeBuilder {

        private String disciplineName;

        private int priorityOrder;
        private DisciplineClassesDTO disciplineClasses;
        private DaysOfWeekDTO daysOfWeek;
        private List<ScheduleClassesDTO> scheduleClasses;

        public GradeBuilder setDisciplineClasses(DisciplineClassesDTO disciplineClasses) {
            this.disciplineClasses = disciplineClasses;
            return this;
        }

        public GradeBuilder setDaysOfWeek(DaysOfWeekDTO daysOfWeek) {
            this.daysOfWeek = daysOfWeek;
            return this;
        }

        public GradeBuilder setScheduleClasses(List<ScheduleClassesDTO> scheduleClasses) {
            this.scheduleClasses = scheduleClasses;
            return this;
        }

        public GradeBuilder setDisciplineName(String disciplineName) {
            this.disciplineName = disciplineName;
            return this;
        }

        public GradeBuilder setPriorityOrder(int priorityOrder) {
            this.priorityOrder = priorityOrder;
            return this;
        }

        public GradeResponseDTO build(){
            return new GradeResponseDTO(this);
        }
    }
}
