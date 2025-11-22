package org.example.kiosk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.kiosk.dto.EventLogRequestDto;
import org.example.kiosk.dto.EventLogResponseDto;
import org.example.kiosk.dto.PageAverageStayTimeDto;
import org.example.kiosk.entity.EventLog;
import org.example.kiosk.service.AnalyticsService;
import org.example.kiosk.service.EventLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-logs")
public class EventLogController {

  private final EventLogService eventLogService;
  private final AnalyticsService analyticsService;

  /**
   * 이벤트 로그 저장
   */
  @PostMapping("/events")
  @Operation(
          summary = "이벤트 로그 저장",
          description = """
                발생한 이벤트 로그를 저장합니다. 
                - STAY_TIME: 화면 머문 시간
                - CLICK_POS: 클릭 좌표
                """
  )
  public void saveEventLog(@RequestBody EventLogRequestDto request) throws JsonProcessingException {
    eventLogService.save(request);
  }

  /**
   * 모든 이벤트 로그 조회
   */
  @GetMapping("/events")
  @Operation(summary = "저장된 모든 이벤트 로그 조회")
  public List<EventLogResponseDto> getEvents() {
    return eventLogService.findAll()
            .stream()
            .map(EventLogResponseDto::from)
            .toList();
  }

  /**
   * 특정 페이지의 평균 머무른 시간 조회
   */
  @GetMapping("/times/average")
  @Operation(summary = "특정 페이지의 평균 머무른 시간 조회")
  public PageAverageStayTimeDto getAverageStayTime(@RequestParam int pageNum) {
    double averageTime = analyticsService.getAverageStayTime(pageNum);
    return new PageAverageStayTimeDto(String.valueOf(pageNum), averageTime);
  }
}