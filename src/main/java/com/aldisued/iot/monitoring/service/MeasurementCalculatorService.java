package com.aldisued.iot.monitoring.service;


import java.util.*;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MeasurementCalculatorService {
  
  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation < 0.0 || deviation > 1.0) {
      throw new IllegalArgumentException(
              "Deviation must be between 0.0 and 1.0"
      );
    }
    
    OptionalDouble avgOptional = values.stream().mapToDouble(Double::doubleValue).average();
    if(avgOptional.isPresent()){
      Double avg = avgOptional.getAsDouble();
      Double allowedDeviation = avg * deviation;
      
      Double upperMargin = avg + allowedDeviation;
      Double lowerMargin = avg - allowedDeviation;
      return values.stream().filter(filterByMargins(lowerMargin, upperMargin)).toList();
    }
    
    return Collections.emptyList();
  }
  
  private static Predicate<Double> filterByMargins(Double lowerMargin, Double upperMargin) {
    return value -> value >= lowerMargin && value <= upperMargin;
  }
  
  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    validate(data, windowSize);
    
    List<Double> movingAverageValues = new ArrayList<>();
    for(int i = 0; i < data.size(); i++){
      if(i + windowSize > data.size()){
        continue;
      }
      
      Double average = 0.00;
      for(int j = i; j < i + windowSize; j++){
        average += data.get(j);
      }
      movingAverageValues.add(average/windowSize);
    }
    
    return movingAverageValues;
  }
  
  private static void validate(List<Double> data, int windowSize) {
    if(CollectionUtils.isEmpty(data)){
      throw new IllegalArgumentException();
    }
    
    if (windowSize <= 0 || windowSize > data.size()) {
      throw new IllegalArgumentException(
              "Deviation must be between 0.0 and 1.0"
      );
    }
  }
  
}
