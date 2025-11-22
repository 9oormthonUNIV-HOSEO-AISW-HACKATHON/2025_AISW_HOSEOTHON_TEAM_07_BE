package org.example.kiosk.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.kiosk.dto.EventLogRequestDto;
import org.example.kiosk.entity.EventLog;
import org.example.kiosk.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventLogService {

  private final EventLogRepository eventLogRepository;

  // 이벤트 로그 저장
  public void save(EventLogRequestDto req) throws JsonProcessingException {
    EventLog log = EventLog.builder()
            .eventName(req.getEventName())
            .createdAt(LocalDateTime.now())
            .payLoad(new ObjectMapper().writeValueAsString(req.getPayLoad()))
            .build();

    eventLogRepository.save(log);
  }


  // 이벤트 로그 반환
  public List<EventLog> findAll() {
    return eventLogRepository.findAll();
  }

}
