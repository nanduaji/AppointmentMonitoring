package com.AppointmentMonitoring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class District implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long districtId;
	@NotEmpty
	private String districtCode;
	@Size(min = 2, message = " district name atleast 2 characters")
	private String districtNameEng;
	@Size(min = 2, message = " district name atleast 2 characters")
	private String districtNameMal;
	
	//default constructor
	public District() {
		
	}
	
	//parameters constructor
	public District(String districtCode, String districtNameEng, String districtNameMal) {
		super();
		this.districtCode = districtCode;
		this.districtNameEng = districtNameEng;
		this.districtNameMal = districtNameMal;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictNameEng() {
		return districtNameEng;
	}
	public void setDistrictNameEng(String districtNameEng) {
		this.districtNameEng = districtNameEng;
	}
	public String getDistrictNameMal() {
		return districtNameMal;
	}
	public void setDistrictNameMal(String districtNameMal) {
		this.districtNameMal = districtNameMal;
	}
	

}
