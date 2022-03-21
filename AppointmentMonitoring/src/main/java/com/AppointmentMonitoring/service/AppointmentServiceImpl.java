package com.AppointmentMonitoring.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppointmentMonitoring.model.Appointments;
import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.model.Employee;
import com.AppointmentMonitoring.repository.AppointmentsRepository;
@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	AppointmentsRepository appointmentRepository;
	
	@Override
	public Optional<Appointments> getAppointment(Long appointmentNumber){
		return appointmentRepository.findById(appointmentNumber);
	}
	@Override
	public void update(Appointments appointment, Long appointmentNumber) {
		appointmentRepository.save(appointment);
		}
	
	
	public void updateField(String meetingCompleted,Long appointmentNumber) {
		appointmentRepository.updateField(meetingCompleted,appointmentNumber);
		System.out.println("INSIDE updateField" + " " + meetingCompleted);
		}
	
	@Override
	public void delete(Long appointmentNumber) {
		appointmentRepository.deleteById(appointmentNumber);	
		}
	@Override
	public List<Appointments> getAllBetweenDates(Date fromDate, Date toDate){
		 return appointmentRepository.findAllByFromDateGreaterThanEqualAndToDateLessThanEqual(fromDate, toDate);	
	}
	
}
