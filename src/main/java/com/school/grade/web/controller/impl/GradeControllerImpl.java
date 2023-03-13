package com.school.grade.web.controller.impl;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.GradeService;
import com.school.grade.web.controller.GradeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GradeControllerImpl implements GradeController {

    @Autowired
    private GradeService gradeService;

    @Override
    public List<GradeResponseDTO> createGrade(GradeRequestDTO gradeRequestDTO) {
        return gradeService.createGrade(gradeRequestDTO);
    }

    @Override
    public void test() {
        String test =  "";
        System.out.println("Deu bom");
    }
}
