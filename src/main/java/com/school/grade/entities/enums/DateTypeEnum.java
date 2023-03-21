package com.school.grade.entities.enums;

import lombok.Getter;

@Getter
public enum DateTypeEnum {

    BUSINESS_DAY("Dia util"),
    OUT_OF_RANGE("Fora do range"),
    HOLIDAY("Feriado"),
    VACATION("Ferias");

    private String description;

    DateTypeEnum(String description) {
        this.description = description;
    }


}


