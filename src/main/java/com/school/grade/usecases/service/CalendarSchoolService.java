package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;

import java.util.List;

public interface CalendarSchoolService {

    void initialize(List<GradeRequestDTO> gradeRequestDTO);
}
