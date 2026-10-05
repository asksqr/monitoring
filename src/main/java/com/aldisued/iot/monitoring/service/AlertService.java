package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.AlertNotFoundException;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertService {

  private final AlertRepository alertRepository;
  private final SensorRepository sensorRepository;
  private final KafkaTemplate<String, AlertDto> kafkaTemplate;

  public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
      KafkaTemplate<String, AlertDto> kafkaTemplate) {
    this.alertRepository = alertRepository;
    this.sensorRepository = sensorRepository;
    this.kafkaTemplate = kafkaTemplate;
  }
  
  @Transactional
  public Alert saveAlert(AlertDto alertDto) throws SensorNotFoundException {
    Sensor sensorFound = sensorRepository.findById(alertDto.sensorId())
            //we assume Sensor is mandatory and want to handle these cases
            .orElseThrow(() -> new SensorNotFoundException("Sensor not found with id: " + alertDto.sensorId()));
    
    Alert alert = new Alert(
            alertDto.message(),
            alertDto.timestamp(),
            sensorFound);
    
	Alert savedAlert = alertRepository.save(alert);
    kafkaTemplate.send("alerts", alertDto);
    return savedAlert;
  }

  public AlertDto findLastAlertBySensorId(UUID sensorId)
          throws AlertNotFoundException {
    Optional<Alert> alertOptional = alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId);
	Alert alert = alertOptional.orElseThrow(() -> new AlertNotFoundException("Alert not found with sensorId: " + sensorId));
    return new AlertDto(alert.getSensor().getId(), alert.getMessage(), alert.getTimestamp());
  }
}
