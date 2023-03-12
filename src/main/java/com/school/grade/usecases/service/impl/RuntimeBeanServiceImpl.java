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
    public void createOrLoadBean(Object object) {

        Object objectDTO;
        String beanName;

        if (object instanceof GradeResponseDTO) {
            objectDTO = createGradeSimpleBean((GradeResponseDTO) object);
            beanName = getGradeBeanName((GradeResponseDTO) object);
        } else {
            objectDTO = (GradeResponseDTO) object;
            beanName = "calendar_school";
        }

        runtimeBeanBuilder.createOrLoadBean(beanName, objectDTO);
    }

    @Override
    public void destroyBean(String beanName) {
        runtimeBeanBuilder.destroyBean(beanName);
    }

    private GradeSimpleBean createGradeSimpleBean(GradeResponseDTO gradeResponse) {
        return new GradeSimpleBean(
                gradeResponse.getScheduleClasses().get(gradeResponse.getScheduleClasses().size() - 1).getDateOfClass(),
                gradeResponse.getPriorityOrder(),
                gradeResponse.getDisciplineName(),
                gradeResponse.getDaysOfWeek()

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