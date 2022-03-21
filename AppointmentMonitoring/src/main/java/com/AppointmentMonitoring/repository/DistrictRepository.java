package com.AppointmentMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.AppointmentMonitoring.model.District;


public interface DistrictRepository extends JpaRepository<District, Long> {
}
