package com.school.grade.usecases.service;

import com.school.grade.entities.dto.grade.request.GradeRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface GradeXlsService {

    GradeRequestDTO convertToGradeRequest(MultipartFile[] files);

}
