package com.AppointmentMonitoring.service;

import java.util.Optional;

import com.AppointmentMonitoring.model.Employee;

public interface EmployeeService {
	
	Optional<Employee> getEmployee(Long employeeCode);
	
	public void update(Employee employee, Long employeeCode);
	
	
	public void delete(Long employeeCode);

}
