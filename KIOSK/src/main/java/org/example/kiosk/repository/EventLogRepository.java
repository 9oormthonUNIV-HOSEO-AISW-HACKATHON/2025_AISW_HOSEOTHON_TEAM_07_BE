package org.example.kiosk.repository;

import org.example.kiosk.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {

  // 이벤트 로그 중에, 화면에 머문 시간이면서 요청하는 페이지인 결과들 반환
  @Query(
          value = "SELECT * FROM event_log " +
                  "WHERE event_name = 'STAY_TIME' " +
                  "AND JSON_EXTRACT(pay_load, '$.pageNum') = :pageNum",
          nativeQuery = true
  )
  List<EventLog> findStayTimeByPage(@Param("pageNum") int pageNum);
}
