package com.school.grade.entities.dto.csv.converter;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractBeanField {
    @Override
    protected Object convert(String s) {

        if (s.equals("data_feriado")
                || s.equals("inicio_semestre")
                || s.equals("final_semestre")
                || s.equals("inicio_ferias")
                || s.equals("final_ferias")
        ) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(s, formatter);
    }
}
