package com.school.grade.entities.dto.grade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class GradeResponseDTO {

    @JsonProperty
    private String disciplineName;
    @JsonProperty
    private int priorityOrder;
    @JsonProperty
    private DaysAndHoursDTO daysAndHours;
    @JsonProperty
    private DaysOfWeekDTO daysOfWeek;
    @JsonProperty
    private List<ScheduleDTO> scheduleClasses;

    private GradeResponseDTO(GradeBuilder gradeBuilder) {
        this.daysAndHours = gradeBuilder.daysAndHours;
        this.daysOfWeek = gradeBuilder.daysOfWeek;
        this.scheduleClasses = gradeBuilder.scheduleClasses;
        this.disciplineName = gradeBuilder.disciplineName;
        this.priorityOrder = gradeBuilder.priorityOrder;
    }

    public static class GradeBuilder {

        private String disciplineName;
        private int priorityOrder;
        private DaysAndHoursDTO daysAndHours;
        private DaysOfWeekDTO daysOfWeek;
        private List<ScheduleDTO> scheduleClasses;

        public GradeBuilder setDaysAndHours(DaysAndHoursDTO daysAndHours) {
            this.daysAndHours = daysAndHours;
            return this;
        }

        public GradeBuilder setDaysOfWeek(DaysOfWeekDTO daysOfWeek) {
            this.daysOfWeek = daysOfWeek;
            return this;
        }

        public GradeBuilder setScheduleClasses(List<ScheduleDTO> scheduleClasses) {
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
