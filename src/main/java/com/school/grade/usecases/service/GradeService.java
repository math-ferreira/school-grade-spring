package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.grade.builder.GradeResponseDTO;

public interface GradeService {

    GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO);
}
