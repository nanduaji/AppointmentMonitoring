package com.AppointmentMonitoring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;



public interface EmployeemasterRepository extends JpaRepository<Employee, Long> {
	
	 List<Employee> findAllByStatusNot(String status);
	 Employee findByEmployeeCode(Long employeeCode);
	@Transactional
	@Modifying
	@Query("update Employee a set district_id = districtId where employee_code=employeeCode")
	void updateDistrictId(@Param(value = "district_id") long districtId,@Param(value = "employee_code") long employeeCode);
	 

}
