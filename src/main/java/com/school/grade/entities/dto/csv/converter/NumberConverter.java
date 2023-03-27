package com.school.grade.entities.dto.csv.converter;

import com.opencsv.bean.AbstractBeanField;

public class NumberConverter extends AbstractBeanField {
    @Override
    protected Object convert(String s) {

        if (s.equals("carga_horaria") || s.equals("prioridade") || s.equals("aulas_por_semana")) {
            return null;
        }
        return Integer.valueOf(s);
    }
}
