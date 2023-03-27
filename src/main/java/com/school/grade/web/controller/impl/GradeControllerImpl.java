package com.school.grade.web.controller.impl;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import com.school.grade.usecases.service.GradeXlsService;
import com.school.grade.web.controller.GradeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GradeControllerImpl implements GradeController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private GradeXlsService gradeXlsService;

    @Override
    public GradeResponseDTO createGrade(
            GradeRequestDTO gradeRequestDTO
    ) {
        return gradeService.createGrade(gradeRequestDTO);
    }

    @Override
    public ResponseEntity<String> createXlsGrade(MultipartFile[] files) {
        GradeRequestDTO gradeRequestDTO = gradeXlsService.convertToGradeRequest(files);
        gradeService.createGrade(gradeRequestDTO);
        return ResponseEntity.ok("\n---> Arquivo criado com sucesso. Verifique a pasta: arquivos_gerados <---\n\n");
    }
}
