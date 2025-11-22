package org.example.kiosk.repository;

import org.example.kiosk.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

  // 이벤트 타입별 조회 기능 구현
}
