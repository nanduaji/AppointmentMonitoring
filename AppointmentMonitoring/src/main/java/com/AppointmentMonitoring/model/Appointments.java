package com.AppointmentMonitoring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Appointments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long appointmentNumber;
	private Long employeeCode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date toDate;
	@NotEmpty
	private String meetingDescription;
	@NotEmpty
	public String agenda;
//	public Boolean meetingCompleted;
	public String meetingCompleted;
	@NotEmpty
	private String fromTime;
	@NotEmpty
	private String toTime;
	
	
	
	
	public Long getAppointmentNumber() {
		return appointmentNumber;
	}
	public void setAppointmentNumber(Long appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}
	public Long getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getMeetingDescription() {
		return meetingDescription;
	}
//	public String getMeetingDescription() {
//		return meetingDescription;
//	}
	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}
	public String getAgenda() {
		return agenda;
	}
	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}
	public String getMeetingCompleted() {
		return meetingCompleted;
	}
	public void setMeetingCompleted(String meetingCompleted) {
		this.meetingCompleted = meetingCompleted;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	

}
