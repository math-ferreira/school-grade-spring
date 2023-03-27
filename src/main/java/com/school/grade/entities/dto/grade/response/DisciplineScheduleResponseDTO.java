package com.school.grade.entities.dto.grade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.grade.entities.dto.grade.DaysAndHoursDTO;
import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import com.school.grade.entities.dto.grade.ScheduleDTO;
import lombok.Getter;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class DisciplineScheduleResponseDTO {

    @JsonProperty
    private String disciplineName;
    @JsonProperty
    private String disciplineInitials;
    private IndexedColors disciplineColor;
    @JsonProperty
    private int priorityOrder;
    private int workload;
    @JsonProperty
    private DaysAndHoursDTO daysAndHours;
    @JsonProperty
    private DaysOfWeekDTO daysOfWeek;
    @JsonProperty
    private List<ScheduleDTO> scheduleClasses;



    private DisciplineScheduleResponseDTO(GradeBuilder gradeBuilder) {
        this.daysAndHours = gradeBuilder.daysAndHours;
        this.daysOfWeek = gradeBuilder.daysOfWeek;
        this.scheduleClasses = gradeBuilder.scheduleClasses;
        this.disciplineName = gradeBuilder.disciplineName;
        this.priorityOrder = gradeBuilder.priorityOrder;
        this.disciplineInitials = gradeBuilder.disciplineInitials;
        this.disciplineColor = getRandomColor();
        this.workload = gradeBuilder.workload;
    }


    private IndexedColors getRandomColor() {
        // List<Integer> availableColors = List.of(56, 62, 61);
        List<Integer> availableColors = Arrays.stream(IndexedColors.values())
                .filter(color ->
                        !color.toString().contains("BLACK")
                                && !color.toString().contains("WHITE")
                                && !color.toString().contains("RED")
                                && !color.toString().contains("GREY")
                                && !color.toString().contains("DARK")
                )
                .map(color -> Integer.valueOf(color.index))
                .toList();

        Random rand = new Random();
        int result = availableColors.get(rand.nextInt(availableColors.size()));

        return IndexedColors.fromInt(result);
    }

    public static class GradeBuilder {
        private String disciplineName;
        private String disciplineInitials;
        private int priorityOrder;
        private int workload;
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

        public GradeBuilder setDisciplineInitials(String disciplineInitials) {
            this.disciplineInitials = disciplineInitials;
            return this;
        }

        public GradeBuilder setPriorityOrder(int priorityOrder) {
            this.priorityOrder = priorityOrder;
            return this;
        }

        public GradeBuilder setWorkload(int workload) {
            this.workload = workload;
            return this;
        }

        public DisciplineScheduleResponseDTO build(){
            return new DisciplineScheduleResponseDTO(this);
        }

    }
}
