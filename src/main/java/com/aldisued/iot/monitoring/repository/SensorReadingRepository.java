package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {
	
	@Query("""
			SELECT AVG(sr.value)
			FROM SensorReading sr
			WHERE sr.timestamp BETWEEN :from AND :to and sr.sensor.type = TEMPERATURE
			""")
	Optional<Double> getAverageTemperature(
			LocalDateTime from,
			LocalDateTime to
	);
	
	@Query("""
			SELECT sr.value
			FROM SensorReading sr
			WHERE sr.timestamp BETWEEN :from AND :to and sr.sensor.type = :sensorType
			ORDER BY sr.timestamp
			""")
	List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from, LocalDateTime to);
}
