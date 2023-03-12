package com.school.grade.entities;

import com.school.grade.entities.dto.grade.DaysOfWeekDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GradeSimpleBean implements Comparable<GradeSimpleBean>{

    private LocalDate lastClassDate;
    private int priorityOrder;
    private String disciplineName;
    DaysOfWeekDTO daysOfWeek;

    @Override
    public int compareTo(GradeSimpleBean anotherObject) {
        int compareDate = lastClassDate.compareTo(anotherObject.lastClassDate);
        if (compareDate == 0) {
            if(priorityOrder > anotherObject.priorityOrder) {
                return 1;
            } else if(priorityOrder == anotherObject.priorityOrder){
                throw new RuntimeException("Invalid priority order for disciplines: (1): " + disciplineName + " and (2):" + anotherObject.disciplineName);
            } else {
                return -1;
            }
        } else {
            return compareDate;
        }
    }
}
