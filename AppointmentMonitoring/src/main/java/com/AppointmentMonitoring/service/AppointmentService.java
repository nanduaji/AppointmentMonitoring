package com.AppointmentMonitoring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.AppointmentMonitoring.model.Appointments;
import com.AppointmentMonitoring.model.District;


public interface AppointmentService {
	
	Optional<Appointments> getAppointment(Long appointmentNumber);
	
	public void update(Appointments appointment, Long appointmentNumber);
	
	public void delete(Long appointmentNumber);
	
	public List<Appointments> getAllBetweenDates(Date fromDate, Date toDate);

	void updateField(String meetingCompleted, Long appointmentNumber);

}
