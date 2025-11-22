package org.example.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 모든 필드값을 받는 생성자 자동 생성
@Builder            // 객체 생성 시, 빌더 패턴
public class EventLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long eventLogId;

  @Column(nullable = false)
  private String eventName; // 이벤트 종류 (화면 머문 시간, 클릭 좌표)

  @Column(nullable = false)
  private OffsetDateTime createdAt; // 로그 저장 시간

  @Lob
  @Column(columnDefinition = "json") // 이벤트별 상세 데이터
  private String payLoad;
}
