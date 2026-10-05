package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SensorReadingService {

  private final SensorReadingRepository sensorReadingRepository;
  private final SensorRepository sensorRepository;

  public SensorReadingService(SensorReadingRepository sensorReadingRepository,
      SensorRepository sensorRepository) {
    this.sensorReadingRepository = sensorReadingRepository;
    this.sensorRepository = sensorRepository;
  }
  
  @Transactional
  public SensorReading saveSensorReading(SensorReadingDto sensorReadingDto) throws SensorNotFoundException {
	Sensor sensorFound = sensorRepository.findById(sensorReadingDto.sensorId())
            //we assume Sensor is mandatory and want to handle these cases
            .orElseThrow(() -> new SensorNotFoundException("Sensor not found for id: " + sensorReadingDto.sensorId()));
    
    SensorReading sensorReading = new SensorReading(
            sensorReadingDto.value(),
            sensorReadingDto.timestamp(),
            sensorFound);
    return sensorReadingRepository.save(sensorReading);
  }
}
