package com.school.grade.web.controller;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.grade.builder.GradeResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "grade", description = "Provide functions related to school grade")
@RequestMapping("/grade")
public interface GradeController {

    @PostMapping("/create")
    GradeResponseDTO createGrade(@RequestBody GradeRequestDTO gradeRequestDTO);

    @GetMapping("/test")
    void test();

}
