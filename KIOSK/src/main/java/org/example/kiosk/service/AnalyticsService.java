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
@Slf4j   // 로그 사용 가능!
@RequiredArgsConstructor
public class AnalyticsService {

  private final EventLogRepository eventLogRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public double getAverageStayTime(int pageNum) {

    List<EventLog> logs = eventLogRepository.findStayTimeByPage(pageNum);

    log.info("📌 pageNum={} 에 대한 STAY_TIME 로그 {}개 조회됨", pageNum, logs.size());

    // userCount → totalStaySeconds
    Map<Integer, Double> userTotals = new HashMap<>();

    for (EventLog log : logs) {
      Map<String, Object> payLoad = parsePayload(log.getPayLoad());

      int userCount = (int) payLoad.get("userCount");
      double stay = ((Number) payLoad.get("staySeconds")).doubleValue();

      userTotals.put(userCount, userTotals.getOrDefault(userCount, 0.0) + stay);

    }

    if (userTotals.isEmpty()) {
      log.warn("⚠️ pageNum={} 에 대한 로그가 없어 평균 계산 불가 (0 반환)", pageNum);
      return 0.0;
    }

    // 사용자 총합 / 평균 계산
    double sum = userTotals.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();

    int userCount = userTotals.size();
    double avg = sum / userCount;

    // 상세 로그 출력
    log.info("----- 📊 페이지 {} 평균 머무른 시간 계산 결과 -----", pageNum);
    log.info("👤 사용자별 머문 시간 총합: {}", userTotals);
    log.info("🔢 사용자 수: {}", userCount);
    log.info("🧮 총합 staySeconds = {}", sum);
    log.info("📈 평균 staySeconds = {}", avg);
    log.info("------------------------------------------------");

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