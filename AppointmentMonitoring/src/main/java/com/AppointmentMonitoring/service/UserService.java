package com.AppointmentMonitoring.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;

public interface UserService {
	Users createUser(Employee employee);
	
	Authentication getAuthentication();
    public String getName();
    public UserDetails getUserDetails();

}
