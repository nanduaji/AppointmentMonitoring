package com.AppointmentMonitoring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;
import com.AppointmentMonitoring.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userrepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public Users createUser(Employee employee) {
		Users users = new Users();
		users.setEmployeeCode(employee.getEmployeeCode());
		users.setUsername(employee.getEmployeeNameEng());
		users.setPassword(passwordEncoder.encode(employee.getPassword()));
		users.setRole("ROLE_USER");
		users.setActive(true);
		users.setStatus("Active");
		users.setEnabled(1);
		return userrepository.save(users);
	}
	
	
	
	
	@Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public String getName() {
        return getAuthentication().getName();
    }
    public UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }
}
