package com.AppointmentMonitoring.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;
import com.AppointmentMonitoring.repository.DistrictRepository;
import com.AppointmentMonitoring.repository.EmployeemasterRepository;
import com.AppointmentMonitoring.repository.UserRepository;
import com.AppointmentMonitoring.service.DistrictService;
import com.AppointmentMonitoring.service.EmployeeService;
import com.AppointmentMonitoring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
public class employeeController {
	
	@Autowired
	EmployeemasterRepository employeeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
    UserService userService;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	DistrictService districtService;
	@Autowired
	EmployeeService employeeservice;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	 @GetMapping("/public/register")
	    public String viewRegistrationPage(Model model) {
	    	model.addAttribute("employee", new Employee());
	    	List<District> districtlist= districtRepository.findAll();
	    	model.addAttribute("districtlist", districtlist);
	        return "registerform";
	    }
	 @PostMapping("/public/register")
	    public String employeeRegister(@Valid Employee employeemaster, BindingResult result) {
		 if (result.hasErrors()) {
	            return "registerform";
	        }
		String msg= "";
	    	employeemaster.setActive(true);
	    	employeemaster.setStatus("Active");
	    	try {
	    		
		        employeeRepository.save(employeemaster);
		        userService.createUser(employeemaster);
		        msg="redirect:../login";
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    	return msg;
	        
	    }
	 
	 @GetMapping("/employeelist")
	    public String viewEmployeelist(Model model) {
		 List<Employee> listemployee = employeeRepository.findAllByStatusNot("Deleted");
	        model.addAttribute("listemployee", listemployee);
	        return "employees";
	    }
	 @GetMapping("/ViewEmployeeAddForm")
	    public String ViewEmployeeAdd(Model model) {
	    	model.addAttribute("employee", new Employee());
	    	List<District> districtlist= districtRepository.findAll();
	    	model.addAttribute("districtlist", districtlist);
	        return "employeeadd";
	    }
	 @PostMapping("/AddEmployee")
	    public String AddEmployee(@Valid Employee employeemaster, BindingResult result) {
		 employeemaster.setActive(true);
		 employeemaster.setStatus("Active");
		 if (result.hasErrors()) {
	            return "employeeadd";
	        }
	     userService.createUser(employeemaster);
		 employeemaster.setPassword(passwordEncoder.encode(employeemaster.getPassword()));
		 employeeRepository.save(employeemaster);
	         
	        return "redirect:employeelist";
	    }
	 @GetMapping("/employeeView/{employeeCode}")
	 public String employeeViewMethod(@PathVariable Long employeeCode, Model model) {
//		 Optional<Employee> employee = employeeservice.getEmployee(employeeCode);
		 Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
		 model.addAttribute("employee", employee);
		 return "employeeview";
	 }
	 
	 @GetMapping("/employeeEdit/{employeeCode}")
	 public String employeeEditMethod(@PathVariable Long employeeCode, Model model) {
//		 Optional<Employee> employee = employeeservice.getEmployee(employeeCode);
		 Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
		 model.addAttribute("employee", employee);
		 List<District> districtlist= districtRepository.findAll();
	    model.addAttribute("districtlist", districtlist);
	    String selectedDistrict = employee.getDistrictId().getDistrictNameEng();
	    model.addAttribute("selectedDistrict", selectedDistrict);
	    String districtCode = employee.getDistrictId().getDistrictCode();
	    model.addAttribute("districtCode", districtCode);
		 return "employeeEdit";
	 }
	 
	 @PutMapping("/employeeUpdate")
	 public String employeeUpdateMethod(@Valid Employee employee,BindingResult result , @RequestParam(value = "districtId", required = true) String districtId) {
		 //employee.setPhoto(filename);	
		 long employeeCode= employee.getEmployeeCode();
		 Employee dbemployee = employeeRepository.findByEmployeeCode(employeeCode);
		 System.out.println("DBEMPLOYEE" + districtId);
		 if (result.hasErrors()) {
//			 if(employee.districtId == null) {
//				employee.districtId.districtId = dbemployee.getDistrictId().getDistrictId();
//				 System.out.println("EMPLOYEE" + employee.districtId.districtId);
//			 }
	            return "employeeEdit";
	        }

		
		
		employee.setActive(true);
		String status= "Active";
		String dbstatus = dbemployee.getStatus();
		
//		if((dbemployee.get().getStatus()) == "Active") {
		
		if(status.equals(dbstatus)) {
			employee.setStatus("Active");
		}else {
			employee.setStatus("InActive");
		}
		employeeservice.update(employee, employeeCode);
		return "redirect:employeelist";
		
	 }
	 
	 @GetMapping("/employeeDeleteForm/{employeeCode}")
	 public String districtDeleteFormMethod(@PathVariable Long employeeCode,Model model) {
//		 Optional<Employee> employee = employeeservice.getEmployee(employeeCode);
		 Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
		 model.addAttribute("employee", employee);
		 return "employeeDeleteView";
	 }
	 
	 @GetMapping("/Deleteemployee/{employeeCode}")
	 public String districtDeleteMethod(Employee employee , Users user, @PathVariable Long employeeCode) { 
		 //Optional<Employee> emp = employeeservice.getEmployee(employeeCode);
		 //Employee getemp = employeeRepository.getById(employeeCode);
		 //getemp.setStatus("Deleted");
		 
		 Employee emp = employeeRepository.findById(employeeCode).get();
		 emp.setStatus("Deleted");
		 employeeRepository.save(emp);
		 
		 Users usr = userRepository.findByEmployeeCode(employeeCode);
		 usr.setStatus("Deleted");
		 usr.setEnabled(0);
		 userRepository.save(usr);
		 
		 return "redirect:../employeelist";
	 }
	 
	 @GetMapping("/Deactivate/{employeeCode}")
	    public String Deactivate(Employee employee , Users user, @PathVariable Long employeeCode) {
		 Employee emp = employeeRepository.findById(employeeCode).get();
		 emp.setStatus("InActive");
		 employeeRepository.save(emp);
		 
		 Users usr = userRepository.findByEmployeeCode(employeeCode);
		 usr.setStatus("InActive");
		 usr.setEnabled(0);
		 userRepository.save(usr);
		 
		 return "redirect:../employeelist";
	    }
	 @GetMapping("/Activate/{employeeCode}")
	    public String Activate(Employee employee , Users user, @PathVariable Long employeeCode) {
		// long employeeCode =  Long.parseLong(employeeCodeval.trim());
		 Employee emp = employeeRepository.findById(employeeCode).get();
		 emp.setStatus("Active");
		 employeeRepository.save(emp);
		 
		 Users usr = userRepository.findByEmployeeCode(employeeCode);
		 usr.setStatus("Active");
		 usr.setEnabled(1);
		 userRepository.save(usr);
		 
		 return "redirect:../employeelist";
	    }
	

}
