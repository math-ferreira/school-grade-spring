package com.school.grade.entities.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    AVAILABLE("disponivel"),
    NOT_AVAILABLE("nao disponivel");

    private String description;

    StatusEnum(String description) {
        this.description = description;
    }
}
