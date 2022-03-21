package com.AppointmentMonitoring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.repository.DistrictRepository;
import com.AppointmentMonitoring.repository.EmployeemasterRepository;
import com.AppointmentMonitoring.repository.UserRepository;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeemasterRepository employeeRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void update(Employee employee, Long employeeCode) {
		Long districtId = employee.districtId.districtId;
		employeeRepository.save(employee);
		employeeRepository.updateDistrictId(districtId,employeeCode);
		}
	@Override
	public void delete(Long employeeCode) {
		employeeRepository.deleteById(employeeCode);	
		}

//	@Override
//	public void<Employee> getEmployee(Long employeeCode) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	

}
