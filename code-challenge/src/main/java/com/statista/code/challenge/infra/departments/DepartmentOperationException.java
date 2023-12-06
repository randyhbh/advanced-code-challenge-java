package com.statista.code.challenge.infra.departments;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Map;

@Getter
public class DepartmentOperationException extends RuntimeException {

    @Nullable
    private final Map<String, Object> properties;

    public DepartmentOperationException(String beanName, @Nullable Map<String, Object> properties) {
        super("Problems while doing business with department: " + beanName);
        this.properties = properties;
    }
}
