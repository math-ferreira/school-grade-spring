package com.school.grade.usecases.service;

import com.school.grade.entities.GradeSimpleBean;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;

import java.util.Map;

public interface RuntimeBeanService {
    Map<String, GradeSimpleBean> getAllBeans();

    void createOrLoadBean(GradeResponseDTO gradeResponse);
}
