package org.example.kiosk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kiosk.entity.EventLog;
import org.example.kiosk.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

  private final EventLogRepository eventLogRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public double getAverageStayTime(int pageNum) {

    List<EventLog> logs = eventLogRepository.findStayTimeByPage(pageNum);

    // userCount → totalStaySeconds
    Map<Integer, Double> userTotals = new HashMap<>();

    for (EventLog log : logs) {
      Map<String, Object> payLoad = parsePayload(log.getPayLoad());

      int userCount = (int) payLoad.get("userCount");
      double stay = ((Number) payLoad.get("staySeconds")).doubleValue();

      userTotals.put(userCount, userTotals.getOrDefault(userCount, 0.0) + stay);

    }

    if (userTotals.isEmpty()) {
      return 0.0;
    }

    // 사용자 총합 / 평균 계산
    double sum = userTotals.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();

    int userCount = userTotals.size();
    double avg = sum / userCount;

    return avg;
  }

  private Map<String, Object> parsePayload(String json) {
    try {
      return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (Exception e) {
      throw new RuntimeException("JSON 파싱 오류", e);
    }
  }
}