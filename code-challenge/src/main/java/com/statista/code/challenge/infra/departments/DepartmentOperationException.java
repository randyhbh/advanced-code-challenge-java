package com.statista.code.challenge.infra.departments;

import lombok.Getter;

import java.util.Map;

@Getter
public class DepartmentOperationException extends RuntimeException {

    private final Map<String, Object> properties;

    public DepartmentOperationException(String beanName, Map<String, Object> properties) {
        super("Problems while doing business with department: " + beanName);
        this.properties = properties;
    }
}
