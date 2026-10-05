package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.Sensor;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
	
	@Query("SELECT COUNT(s) FROM Sensor s WHERE s.name = :sensorName")
	long countBySensorName(@Param("sensorName") String sensorName);
}
