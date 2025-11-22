package org.example.kiosk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.kiosk.dto.EventLogRequestDto;
import org.example.kiosk.dto.EventLogResponseDto;
import org.example.kiosk.dto.PageAverageStayTimeDto;
import org.example.kiosk.entity.EventLog;
import org.example.kiosk.service.EventLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-logs")
public class EventLogController {

  private final EventLogService eventLogService;

  @PostMapping("/events")
  @Operation(
          description = "발생한 이벤트 로그를 저장합니다. \n" + "- eventName \n" + "  STAY_TIME: 화면에 머문 시간" +
                  " CLICK_POS: 클릭한 좌표"
  )
  public void saveEventLog(@RequestBody EventLogRequestDto req) throws JsonProcessingException {
    eventLogService.save(req);
  }


  @GetMapping("/events")
  @Operation(
          description = "저장한 이벤트 로그를 조회합니다."
  )
  public List<EventLogResponseDto> getEvents() {
    List<EventLog> logs = eventLogService.findAll();

    return logs.stream()
            .map(EventLogResponseDto::from)
            .toList();
  }


  @GetMapping("/times")
  @Operation(
          description = "페이지 별, 평균 머무른 시간을 조회합니다."
  )
  public PageAverageStayTimeDto getTimes(@RequestParam Integer page) {
    return eventLogService.getAvgTime(page);
  }

}
