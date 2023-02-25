package com.school.grade.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "grade", description = "Provide functions related to school grade")
@RestController
@RequestMapping("/google-calendar")
public interface GradeController {

    @GetMapping()
    void createGrade();

}
