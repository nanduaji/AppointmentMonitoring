package com.AppointmentMonitoring.controller;



import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import java.util.Optional;
import java.util.Stack;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AppointmentMonitoring.model.AppointmentPDFExporter;
import com.AppointmentMonitoring.model.Appointments;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.model.Users;
import com.AppointmentMonitoring.repository.AppointmentsRepository;
import com.AppointmentMonitoring.repository.EmployeemasterRepository;
import com.AppointmentMonitoring.repository.UserRepository;
import com.AppointmentMonitoring.service.AppointmentService;
import com.AppointmentMonitoring.service.UserService;
import com.lowagie.text.DocumentException;
import com.sun.java.swing.plaf.windows.resources.windows;


@Controller
public class appointmentsController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	AppointmentsRepository appointmentsRepository;
	@Autowired
	EmployeemasterRepository employeeRepository;
	@Autowired
	AppointmentService appointmentService;
	@GetMapping("/")
	public String viewAppointmentsChart(Model model) {
		var toDate = new Date();
		int completedCount = 0;
		int notcompletedCount = 0;
		List<Appointments> listAppointments= appointmentsRepository.findAllBytoDate(toDate);
		model.addAttribute("listAppointments",listAppointments);
		List<Appointments> completed= appointmentsRepository.findAll();
		model.addAttribute("completed1",completed);
		for(int i = 0 ; i<completed.size();i++) {
	        Appointments appointments = completed.get(i);
	        LocalDate currentDate = LocalDate.now();
	        ZoneId defaultZoneId = ZoneId.systemDefault();
	        Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
	        LocalDate appointmentsToDate = appointments.toDate.toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate();
//	        System.out.println("!!!!!!!!!!!!!!!!" + appointmentsToDate.compareTo(currentDate));
//	        if(appointmentsToDate.compareTo(currentDate) < 0) {
//	        	long appointmentNumber = appointments.getAppointmentNumber();
//	        	//appointmentService.update(appointments, appointmentNumber);
//	        	System.out.println("@@@@@@@@@@@@@@@@@" + appointments + "   "+ appointmentNumber);
//			}
	        var Completed = appointments.meetingCompleted;
	        var Completed1 = "Completed";
	        var Completed2 = "Not Completed";
	        if(Completed1.toString().equalsIgnoreCase(Completed)) {
	        	completedCount = completedCount+1;
	        	model.addAttribute("completedCount",completedCount);
	        	}
	        else if(Completed2.toString().equalsIgnoreCase(Completed)){
	        	notcompletedCount = notcompletedCount+1;
	        	model.addAttribute("notcompletedCount",notcompletedCount);
	        	
	        }
		}
		Users users = userRepository.findByUsername(userService.getName());
		var employeecode= users.getEmployeeCode();
		Employee employee = employeeRepository.findByEmployeeCode(employeecode);
		model.addAttribute("employee",employee);
		int count = listAppointments.size();
		model.addAttribute("count",count);
		return "index";
		}
	@GetMapping("/completed")
	public String completed(Model model) {
		List<Appointments> listAppointments= appointmentsRepository.findAllBymeetingCompleted("Completed");
		model.addAttribute("listAppointments",listAppointments);
		model.addAttribute("Completed", true);
		return "completed";
		
	}
	@GetMapping("/notcompleted")
	public String notcompleted(Model model) {
		List<Appointments> listAppointments= appointmentsRepository.findAllBymeetingCompleted("Not Completed");
		model.addAttribute("listAppointments",listAppointments);
		model.addAttribute("Completed", false);
		return "completed";
		
	}
	@GetMapping("/appointmenttoday")
	public String appointmentToday(Model model) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate currentDate = LocalDate.now();
		List<Appointments> listAppointments= appointmentsRepository.findAll();
		List<Appointments> list=new ArrayList<Appointments>(); 
		for(int i=0;i<listAppointments.size();i++) {
		Appointments appointments = listAppointments.get(i);
		LocalDate appointmentsToDate = appointments.toDate.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		if(currentDate.compareTo(appointmentsToDate) == 0) {
			list.add(appointments);
		}
		}
		model.addAttribute("listAppointments",list);
		return "appointmenttoday";
	}

	@GetMapping("/appointmentlist")
    public String viewAppointmentlist(Model model) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate currentDate = LocalDate.now();
		Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
//		List<Appointments> listAppointments= appointmentsRepository.findAll(Sort.by(Sort.Direction.DESC, "fromDate"));
		List<Appointments> listAppointments= appointmentsRepository.findAll();
		for(int i = 0 ; i<listAppointments.size();i++) {
	        Appointments appointments = listAppointments.get(i);
	       
	        if(appointments.toDate.compareTo(date) < 0) {
	        	//in case of any error change meetingCompleted to Boolean
	        	appointments.meetingCompleted = "Completed";
	        	appointmentUpdateField(appointments.meetingCompleted, appointments.appointmentNumber);
	        }
	        else {
	        	appointments.meetingCompleted = "Not Completed";
	        	appointmentUpdateField(appointments.meetingCompleted, appointments.appointmentNumber);
	        }
	        
		}
		model.addAttribute("listAppointments",listAppointments);
        return "appointments";
    }
	public String appointmentUpdateField(String meetingCompleted,Long appointmentNumber) {
		appointmentService.updateField(meetingCompleted,appointmentNumber);
		return "redirect:appointmentlist";
	}
	 @GetMapping("/ViewAppointmentAddForm")
	    public String ViewAppointmentAdd(Model model) {
		 Users users = userRepository.findByUsername(userService.getName());
		 var employeecode= users.getEmployeeCode();
		 model.addAttribute("appointment", new Appointments());
		 model.addAttribute("employeecode", employeecode);
		 return "appointmentadd";
	 }
	 @PostMapping("/AddAppointment")
	 public String AddAppointment(Appointments appointmentmaster) {
		
		 
		 //String date = appointmentmaster.getFromDateTime().toString();
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		 //LocalDateTime dateTime = LocalDateTime.parse(date , formatter);
		 
		// DateFormat df = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
		 
	   // java.util.Date fromDateTime = new java.util.Date(appointmentmaster.getFromDateTime().getTime());
	    //java.util.Date toDateTime = new java.util.Date(appointmentmaster.getFromDateTime().getTime());
	    
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 var user = auth.getDetails();
		 var user1 = auth.getCredentials();
		 var user2 = auth.getName();
		 var pricipal = auth.getPrincipal();
		 var user4 = auth.getAuthorities();
		 System.out.println("userrrr  "+user);
		 System.out.println("user1  "+user1);
		 System.out.println("user2  "+user2);
		 System.out.println("user3  "+pricipal);
		 System.out.println("user4  "+user4);

		//Users customUser = (Users)auth.getPrincipal();
		 //Long userId = customUser.getEmployeeCode();
		 //System.out.println("useridd ..............  "+userId);
		 
		 //Map<String , Object> userDetails = ((Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttributes();
	        //System.out.println(userDetails.get("name"));
	       // System.out.println(userDetails.get("email"));
	       // System.out.println(userDetails.get("given_name"));
	        
		 appointmentsRepository.save(appointmentmaster);
		 return "redirect:appointmentlist";
	 }
	 
	 @GetMapping("appointmentView/{appointmentNumber}")
	 public String appointmentViewMethod(@PathVariable Long appointmentNumber, Model model) {
		 Users users = userRepository.findByUsername(userService.getName());
		 var employeecode= users.getEmployeeCode();
		 model.addAttribute("employeecode", employeecode);
		 Optional<Appointments> appointment = appointmentService.getAppointment(appointmentNumber);
		 System.out.println("appointment" + employeecode);
		 model.addAttribute("appointment", appointment);
		 model.addAttribute("employeecode", employeecode);
		 return "appointmentview";
	 }
	 @GetMapping("appointmentTodayView/{appointmentNumber}")
	 public String appointmentTodayViewMethod(@PathVariable Long appointmentNumber, Model model) {
		 Users users = userRepository.findByUsername(userService.getName());
		 var employeecode= users.getEmployeeCode();
		 model.addAttribute("employeecode", employeecode);
		 Optional<Appointments> appointment = appointmentService.getAppointment(appointmentNumber);
		 System.out.println("appointment" + employeecode);
		 model.addAttribute("appointment", appointment);
		 model.addAttribute("employeecode", employeecode);
		 return "appointmenttodayview";
	 }
	 
	 @GetMapping("appointmentEdit/{appointmentNumber}")
	 public String appointmentEditMethod(@PathVariable Long appointmentNumber, Model model) {
		 Optional<Appointments> appointment = appointmentService.getAppointment(appointmentNumber);
		 model.addAttribute("appointment", appointment);
		 System.out.println("appointment   "+appointment.get().getFromDate()+ appointment.get().getFromDate());
		 return "appointmentedit";
	 }
	 
	 @PutMapping("/appointmentUpdate")
	 public String appointmentUpdateMethod(Appointments appointment) {
		long appointmentNumber = appointment.getAppointmentNumber();
		appointmentService.update(appointment, appointmentNumber);
		return "redirect:appointmentlist";
	 }
	 @GetMapping("/appointmentDeleteForm/{appointmentNumber}")
	 public String appointmentDeleteFormMethod(@PathVariable Long appointmentNumber,Model model) {
		 Optional<Appointments> appointment = appointmentService.getAppointment(appointmentNumber);
		 model.addAttribute("appointment", appointment);
		 return "appointmentDeleteView";
	 }
	 
	 @GetMapping("/DeleteAppointment/{appointmentNumber}")
	 public String appointmentDeleteMethod(@PathVariable Long appointmentNumber) { 
		 appointmentService.delete(appointmentNumber);
		 return "redirect:../appointmentlist";
	 }
	
	 @GetMapping("/reportlist")
	    public String viewReportlist(Model model , @RequestParam("fromdate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromdate , @RequestParam("todate") @DateTimeFormat(pattern="yyyy-MM-dd") Date todate) {
			List<Appointments> listAppointments= appointmentService.getAllBetweenDates(fromdate, todate);
			//System.out.println("LISTTTT : "+listAppointments.get(0));
			model.addAttribute("listAppointments",listAppointments);
	        return "appointments";
	    }
	 @GetMapping("/exportPDF")
	    public void exportToPDF(HttpServletResponse response, @RequestParam("fromdatepdf") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromdatepdf, @RequestParam("todatepdf") @DateTimeFormat(pattern="yyyy-MM-dd") Date todatepdf) throws DocumentException, IOException {
		
		 response.setContentType("application/pdf");
		 String headerKey = "Content-Disposition";
		 String headerValue="attachment; filename=appointment.pdf";
		 
		 response.setHeader(headerKey, headerValue);
		 
		 List<Appointments> listAppointments= appointmentService.getAllBetweenDates(fromdatepdf, todatepdf);
		 AppointmentPDFExporter exporter = new AppointmentPDFExporter(listAppointments);
		 exporter.export(response);
	    }
	 
	 
}
