package com.AppointmentMonitoring.repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppointmentMonitoring.model.Appointments;


public interface AppointmentsRepository extends JpaRepository<Appointments, Long>{

	public List<Appointments>findAllByFromDateGreaterThanEqualAndToDateLessThanEqual(Date fromDate, Date toDate);

	//findAllByFromDateLessThanEqualAndToDateGreaterThanEqual
	@Transactional
	@Modifying
//	@Query("update Appointments a set meetingCompleted = meetingCompleted where appointmentNumber=appointmentNumber")
	@Query("update Appointments a set meeting_completed = meetingCompleted where appointment_number=appointmentNumber")
	void updateField(@Param(value = "meeting_completed") String meetingCompleted,@Param(value = "appointment_number") long appointmentNumber);

	List<Appointments> findAllBytoDate(Date toDate);
	
	List<Appointments> findAllBymeetingCompleted(String meetingCompleted);
	
}

