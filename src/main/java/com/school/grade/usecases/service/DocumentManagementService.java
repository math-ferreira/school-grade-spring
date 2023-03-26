package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.response.GradeResponseDTO;

public interface DocumentManagementService {

    void createDocument(GradeResponseDTO gradeResponseDTO);

}
