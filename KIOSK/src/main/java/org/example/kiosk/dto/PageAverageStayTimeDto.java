package org.example.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 페이지당 평균 머무른 시간 반환 dto
@Getter
@AllArgsConstructor
public class PageAverageStayTimeDto {
  private String page;
  private Double averageStaySeconds;
}
