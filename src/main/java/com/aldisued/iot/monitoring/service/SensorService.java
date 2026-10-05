package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.SensorNameInvalidException;
import com.aldisued.iot.monitoring.exception.SensorNameNotUniqueException;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

  private final SensorRepository sensorRepository;

  public SensorService(SensorRepository sensorRepository) {
    this.sensorRepository = sensorRepository;
  }

  public Sensor saveSensor(SensorDto sensor) {
    return sensorRepository.save(new Sensor(
        sensor.name(),
        sensor.type()
    ));
  }
  
  public void validateSensor(String sensorName)
		  throws SensorNameInvalidException, SensorNameNotUniqueException {
    if (isSensorNameValid(sensorName)) {
      throw new SensorNameInvalidException(
              "SensorName is empty"
      );
    }
    
    if (isSensorNameUnique(sensorName)) {
      throw new SensorNameNotUniqueException(
              "SensorName: " + sensorName + " already exists"
      );
    }
  }
  
  public boolean isSensorNameUnique(String sensorName) {
    return sensorRepository.countBySensorName(sensorName) > 0;
  }
  
  public boolean isSensorNameValid(String sensorName) {
    return isEmpty(sensorName);
  }
  
  private static boolean isEmpty(String name) {
    return name == null || name.isEmpty();
  }
}
