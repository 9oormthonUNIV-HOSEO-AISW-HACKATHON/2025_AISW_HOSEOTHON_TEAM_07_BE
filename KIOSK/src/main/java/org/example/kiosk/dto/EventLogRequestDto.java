package org.example.kiosk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.Map;

@Getter
public class EventLogRequestDto { // 이벤트 로그 저장 dto

  @Schema(
          description = "이벤트 이름",
          example = "STAY_TIME"
  )
  private String eventName;

  @Schema(
          description = "이벤트 상세 데이터",
          example = """
            {
              "userCount" : 1
              "pageNum": 3,
              "staySeconds": 12
            }
            """
  )
  private Map<String, Object> payLoad;
}