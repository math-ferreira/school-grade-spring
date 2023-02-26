package com.school.grade.web.controller;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "grade", description = "Provide functions related to school grade")
@RequestMapping("/grade")
public interface GradeController {

    @PostMapping("/create")
    GradeResponseDTO createGrade(@RequestBody GradeRequestDTO gradeRequestDTO);

    @GetMapping("/test")
    void test();

}
