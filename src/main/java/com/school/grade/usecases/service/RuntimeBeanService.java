package com.school.grade.usecases.service;

import com.school.grade.entities.GradeSimpleBean;

import java.util.Map;

public interface RuntimeBeanService {
    Map<String, GradeSimpleBean> getAllBeans();

    void createOrLoadBean(Object object);

    void destroyBean(String beanName);
}
