package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;

public interface GradeService {

    GradeResponseDTO createGrade(GradeRequestDTO gradeRequestDTO);

}
