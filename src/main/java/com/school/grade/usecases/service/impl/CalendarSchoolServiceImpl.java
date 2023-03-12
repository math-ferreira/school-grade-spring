package com.school.grade.usecases.service.impl;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.usecases.service.CalendarSchoolService;
import com.school.grade.usecases.service.RuntimeBeanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CalendarSchoolServiceImpl implements CalendarSchoolService {

    @Autowired
    private RuntimeBeanService runtimeBeanService;

    @Override
    public void initialize(List<GradeRequestDTO> gradeRequestDTO) {

    }
}
