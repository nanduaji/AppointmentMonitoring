 package com.AppointmentMonitoring.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.AppointmentMonitoring.model.Appointments;
import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;
import com.AppointmentMonitoring.repository.DistrictRepository;
import com.AppointmentMonitoring.repository.EmployeemasterRepository;
import com.AppointmentMonitoring.repository.UserRepository;
import com.AppointmentMonitoring.service.DistrictService;
import com.AppointmentMonitoring.service.EmployeeService;
import com.AppointmentMonitoring.service.UserService;





@Controller
public class userController  {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	EmployeemasterRepository employeeRepository;
	@Autowired
	EmployeeService employeeservice;
	@Autowired
	DistrictRepository districtRepository;

	
	

	
    
    @GetMapping("/login")
    public String viewLoginPage(Model model) {
    	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + new Users() );
    	model.addAttribute("user", new Users());
        return "login";
    }
    @PostMapping("/login")
    public String login(Users user) {
    	
    	Users users = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

    	if(users==null) {
    		System.out.println("LOGIN FAILED");
    	}else  {
    		System.out.println("LOGIN SUCCESS");

    	}
        return "index";
    }
    @GetMapping("/logout")
    public String logout(Model model) {
    	model.addAttribute("user", new Users());
        return "login";
    }
    
//    protected Integer getUserIdFromRequest(HttpServletRequest request)  {
//
//		Integer userId = null;
//		try {
//			userId = (Integer) request.getAttribute(SupportAppConstants.USER_ID_REQUEST_ATTRIBUTE);
//		} catch (Exception ex) {
//			// handleExceptions(ex, logger);
//		}
//		return userId;
//
//	}
   

    
    
    @GetMapping("/profile")
    public String viewProfile( Users user, Model model, HttpServletRequest request) {
    	
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println("detail"+auth.getDetails());
    	System.out.println("principal"+auth.getPrincipal());
		
	System.out.println("nameee"+userService.getName());
	System.out.println("userdetails"+userService.getUserDetails());
	
	//By using username take employeecode.... (correction needed ) 
	
	Users users = userRepository.findByUsername(userService.getName());
	var employeecode= users.getEmployeeCode();
	Employee employee = employeeRepository.findByEmployeeCode(employeecode);
	
	System.out.println("desi..  "+ employee.getDesignationEng());
	System.out.println("$$$$$$$$$$$$$$"+ employee.getPhoto());
	model.addAttribute("employee",employee);
	model.addAttribute("users",users);
	
        return "profile";
    }
    
    @GetMapping("/editprofile/{employeeCode}")
    public String editProfile(@PathVariable Long employeeCode, Model model) {
//    	Optional<Employee> employee = employeeservice.getEmployee(employeeCode);
    	Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
		model.addAttribute("employee", employee);
		List<District> districtlist= districtRepository.findAll();
		String selectedDistrict = employee.getDistrictId().getDistrictNameEng();
	    String districtCode = employee.getDistrictId().getDistrictCode();
	    Long districtId = employee.getDistrictId().getDistrictId();
	    model.addAttribute("districtlist", districtlist);
	    model.addAttribute("selectedDistrict", selectedDistrict);
	    model.addAttribute("districtCode",districtCode);
	    model.addAttribute("districtId",districtId);
    	 return "profileedit";
    }
    
    
}
