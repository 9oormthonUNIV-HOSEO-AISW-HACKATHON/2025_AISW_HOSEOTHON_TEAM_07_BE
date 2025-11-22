package org.example.kiosk.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.kiosk.dto.ClickPositionDto;
import org.example.kiosk.dto.EventLogRequestDto;
import org.example.kiosk.dto.PageAverageStayTimeDto;
import org.example.kiosk.entity.EventLog;
import org.example.kiosk.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventLogService {

  private final EventLogRepository eventLogRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  // 이벤트 로그 저장
  public void save(EventLogRequestDto req) throws JsonProcessingException {
    EventLog log = EventLog.builder()
            .eventName(req.getEventName())
            .createdAt(OffsetDateTime.now(ZoneId.of("Asia/Seoul")))
            .payLoad(new ObjectMapper().writeValueAsString(req.getPayLoad()))
            .build();

    eventLogRepository.save(log);
  }


  // 이벤트 로그 전체 반환
  public List<EventLog> findAll() {
    return eventLogRepository.findAll();
  }

  public List<ClickPositionDto> getTouchEventsByPage(int pageNum) {

    List<EventLog> logs = eventLogRepository.findTouchEventsByPage(pageNum);

    return logs.stream()
            .map(log -> {
              Map<String, Object> payload = parsePayload(log.getPayLoad());

              return new ClickPositionDto(
                      ((Number) payload.get("x")).doubleValue(),
                      ((Number) payload.get("y")).doubleValue()
              );
            })
            .toList();
  }

  private Map<String, Object> parsePayload(String json) {
    try {
      return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (Exception e) {
      throw new RuntimeException("JSON 파싱 오류", e);
    }
  }





}
