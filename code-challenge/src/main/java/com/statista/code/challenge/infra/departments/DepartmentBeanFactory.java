package com.statista.code.challenge.infra.departments;

import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.domain.department.DepartmentFactory;
import com.statista.code.challenge.domain.department.DepartmentOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DepartmentBeanFactory implements DepartmentFactory, InitializingBean {

    private final Map<String, DepartmentOperation<?>> beansFactory;

    private final Map<String, DepartmentOperation<?>> departmentsMap;

    public DepartmentBeanFactory(Map<String, DepartmentOperation<?>> beansFactory) {
        this.beansFactory = beansFactory;
        departmentsMap = new HashMap<>();
    }

    public DepartmentOperation<?> getDepartmentTypeBean(Department department) {
        return departmentsMap.get(department.name());
    }

    //Invoked by the containing BeanFactory after it has set all bean
    //properties and satisfied BeanFactoryAware, ApplicationContextAware etc.
    //This method allows the bean instance to perform validation of its
    //overall configuration and final initialization when all bean properties have been set.
    @Override
    public void afterPropertiesSet() throws Exception {
        initializeTypeBeanMap();
    }

    //Method to initialize or create a map to store animalType and corresponding bean of animal
    private void initializeTypeBeanMap() {
        beansFactory.values().forEach(beanOperation -> departmentsMap.put(beanOperation.getBeanName(), beanOperation));
    }
}

