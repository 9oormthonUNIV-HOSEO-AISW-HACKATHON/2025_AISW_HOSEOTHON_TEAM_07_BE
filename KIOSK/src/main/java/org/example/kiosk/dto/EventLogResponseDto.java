package org.example.kiosk.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class EventLogResponseDto { // 이벤트 로그 반환 dto
  private Long eventLogId;
  private String eventName;
  private OffsetDateTime createdAt;
  private Map<String, Object> payLoad;

  public static EventLogResponseDto from(org.example.kiosk.entity.EventLog eventLog) {
    return EventLogResponseDto.builder()
            .eventLogId(eventLog.getEventLogId())
            .eventName(eventLog.getEventName())
            .createdAt(eventLog.getCreatedAt())
            .payLoad(parseJson(eventLog.getPayLoad()))
            .build();
  }

  private static Map<String, Object> parseJson(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (Exception e) {
      throw new RuntimeException("JSON 파싱 오류", e);
    }
  }
}