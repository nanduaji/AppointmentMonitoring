package com.AppointmentMonitoring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class Employee implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeeCode;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	//make updatable field false in case any error occurs 
	@JoinColumn(name = "districtId", referencedColumnName = "districtId", insertable = true, updatable = true)
	public District districtId;
	@Size(min = 2, message = " Employee name atleast 2 characters")
	private String employeeNameEng;
	@Size(min = 2, message = " Employee name atleast 2 characters")
	private String employeeNameMal;
	@Size(min = 2, message = " designation contains atleast 2 characters")
	private String designationMal;
	@Size(min = 2, message = " designation contains atleast 2 characters")
	private String designationEng;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date postingFromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date postingToDate;
//	@NotEmpty
	private String password;
	private String reportingOfficerCode;
	private Boolean active;
	public String photo;
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}
	public District getDistrictId() {
		return districtId;
	}
	public void setDistrictId(District districtId) {
		this.districtId = districtId;
	}
	public String getEmployeeNameEng() {
		return employeeNameEng;
	}
	public void setEmployeeNameEng(String employeeNameEng) {
		this.employeeNameEng = employeeNameEng;
	}
	public String getEmployeeNameMal() {
		return employeeNameMal;
	}
	public void setEmployeeNameMal(String employeeNameMal) {
		this.employeeNameMal = employeeNameMal;
	}
	public String getDesignationMal() {
		return designationMal;
	}
	public void setDesignationMal(String designationMal) {
		this.designationMal = designationMal;
	}
	public String getDesignationEng() {
		return designationEng;
	}
	public void setDesignationEng(String designationEng) {
		this.designationEng = designationEng;
	}
	public Date getPostingFromDate() {
		return postingFromDate;
	}
	public void setPostingFromDate(Date postingFromDate) {
		this.postingFromDate = postingFromDate;
	}
	public Date getPostingToDate() {
		return postingToDate;
	}
	public void setPostingToDate(Date postingToDate) {
		this.postingToDate = postingToDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReportingOfficerCode() {
		return reportingOfficerCode;
	}
	public void setReportingOfficerCode(String reportingOfficerCode) {
		this.reportingOfficerCode = reportingOfficerCode;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String filename) {
		System.out.println("INSIDE SETPHOTO" + filename);
		this.photo = filename;
	}
	
}
