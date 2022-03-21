package com.AppointmentMonitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.repository.DistrictRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	DistrictRepository districtRepository;
	
	@Override
	public List<District> getAllDistrict(){
		return(List<District>) districtRepository.findAll();
	}
	@Override
	public Optional<District> getDistrict(Long districtId) {
		return districtRepository.findById(districtId);
		
	}
	
	@Override
	public void update(District district, Long districtId) {
		districtRepository.save(district);
		}
	
	@Override
	public void delete(Long districtId) {
		districtRepository.deleteById(districtId);	
		}

}
