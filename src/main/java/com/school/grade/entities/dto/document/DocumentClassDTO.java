package com.school.grade.entities.dto.document;

import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

@Data
public class DocumentClassDTO implements Comparable<DocumentClassDTO> {
    private LocalDate dateOfClass;
    private DayOfWeek dayOfWeek;
    private String disciplineInitials;
    private IndexedColors disciplineColor;

    public DocumentClassDTO(
            LocalDate dateOfClass,
            DayOfWeek dayOfWeek,
            String disciplineInitials,
            IndexedColors disciplineColor
    ) {
        this.dateOfClass = dateOfClass;
        this.dayOfWeek = dayOfWeek;
        this.disciplineInitials = disciplineInitials;
        this.disciplineColor = disciplineColor;
    }

    public void setDisciplineColor(IndexedColors currentColor) {
        this.disciplineColor = Arrays.stream(IndexedColors.values())
                .filter(color -> color != currentColor)
                .findAny()
                .get();
    }

    @Override
    public int compareTo(DocumentClassDTO o) {
        return this.getDisciplineColor().compareTo(o.disciplineColor);
    }
}
