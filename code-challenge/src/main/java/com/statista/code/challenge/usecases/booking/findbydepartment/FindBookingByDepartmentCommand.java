package com.statista.code.challenge.usecases.booking.findbydepartment;

import com.statista.code.challenge.domain.Department;
import lombok.Getter;

@Getter
public class FindBookingByDepartmentCommand {
    private final Department department;

    private FindBookingByDepartmentCommand(Department department) {
        this.department = department;
    }

    public static FindBookingByDepartmentCommand fromRequest(Department department) {
        return new FindBookingByDepartmentCommand(department);
    }
}
