package com.AppointmentMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AppointmentMonitoring.model.*;



public interface UserRepository extends JpaRepository<Users, Long> {
	
	Users findByUsernameAndPassword(String userName, String password);
	Users findByEmployeeCode(Long employeeCode);
	Users findByUsername(String userName);
}
