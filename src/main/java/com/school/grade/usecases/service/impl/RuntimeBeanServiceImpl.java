package com.school.grade.usecases.service.impl;

import com.school.grade.config.RuntimeBeanBuilder;
import com.school.grade.entities.GradeSimpleBean;
import com.school.grade.entities.dto.grade.response.GradeResponseDTO;
import com.school.grade.usecases.service.RuntimeBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuntimeBeanServiceImpl implements RuntimeBeanService {

    @Autowired
    private RuntimeBeanBuilder runtimeBeanBuilder;

    @Override
    public Map<String, GradeSimpleBean> getAllBeans() {
        return (Map<String, GradeSimpleBean>) runtimeBeanBuilder.getAllBeans(GradeSimpleBean.class);
    }

    @Override
    public void createOrLoadBean(GradeResponseDTO gradeResponse) {
        GradeSimpleBean gradeSimpleBean = createGradeSimpleBean(gradeResponse);
        String gradeBeanName = getGradeBeanName(gradeResponse);
        runtimeBeanBuilder.createOrLoadBean(gradeBeanName, gradeSimpleBean);
    }

    private GradeSimpleBean createGradeSimpleBean(GradeResponseDTO gradeResponse) {
        return new GradeSimpleBean(
                gradeResponse.getScheduleClasses().get(gradeResponse.getScheduleClasses().size() - 1).getDateOfClass(),
                gradeResponse.getPriorityOrder(),
                gradeResponse.getDisciplineName()
        );
    }

    private static String getGradeBeanName(GradeResponseDTO gradeResponse) {
        return new StringBuilder()
                .append(gradeResponse.getDisciplineName())
                .append("_p")
                .append(gradeResponse.getPriorityOrder())
                .toString()
                .toLowerCase()
                .replace(" ", "_");
    }
}
