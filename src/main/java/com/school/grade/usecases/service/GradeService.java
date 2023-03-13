package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;

import java.util.List;

public interface GradeService {

    List<GradeResponseDTO> createGrade(GradeRequestDTO gradeRequestDTO);
}
