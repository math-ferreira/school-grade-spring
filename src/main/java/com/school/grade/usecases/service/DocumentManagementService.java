package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.response.DisciplineScheduleResponseDTO;

import java.util.List;

public interface DocumentManagementService {

    void createDocument(List<DisciplineScheduleResponseDTO> gradeResponseList);

}
