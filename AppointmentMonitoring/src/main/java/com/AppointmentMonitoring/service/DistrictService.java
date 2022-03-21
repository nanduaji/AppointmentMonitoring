package com.AppointmentMonitoring.service;

import java.util.List;
import java.util.Optional;

import com.AppointmentMonitoring.model.District;

public interface DistrictService {
	
	 List<District> getAllDistrict();
	
	Optional<District> getDistrict(Long districtId);
	
	public void delete(Long districtId);
	
	public void update(District district, Long districtId);
	

	

}
