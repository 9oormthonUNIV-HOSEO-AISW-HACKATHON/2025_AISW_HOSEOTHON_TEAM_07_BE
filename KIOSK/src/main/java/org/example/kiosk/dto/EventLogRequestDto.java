package org.example.kiosk.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class EventLogRequestDto { // 이벤트 로그 저장 dto
  private String eventName;
  private Map<String, Object> payLoad;
}
