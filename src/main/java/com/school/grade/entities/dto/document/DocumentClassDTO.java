package com.school.grade.entities.dto.document;

import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class DocumentClassDTO implements Comparable<DocumentClassDTO> {
    private LocalDate dateOfClass;
    private DayOfWeek dayOfWeek;
    private String disciplineInitials;
    private String disciplineName;
    private String teacherName;
    private IndexedColors disciplineColor;
    private int workload;

    public DocumentClassDTO(
            LocalDate dateOfClass,
            DayOfWeek dayOfWeek,
            String disciplineInitials,
            String disciplineName,
            String teacherName,
            int workload,
            IndexedColors disciplineColor
    ) {
        this.dateOfClass = dateOfClass;
        this.dayOfWeek = dayOfWeek;
        this.disciplineInitials = disciplineInitials;
        this.disciplineName = disciplineName;
        this.teacherName = teacherName;
        this.workload = workload;
        this.disciplineColor = disciplineColor;
    }

    @Override
    public int compareTo(DocumentClassDTO o) {
        return this.getDisciplineColor().compareTo(o.disciplineColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentClassDTO that = (DocumentClassDTO) o;
        return disciplineInitials.equals(that.disciplineInitials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disciplineInitials);
    }
}
