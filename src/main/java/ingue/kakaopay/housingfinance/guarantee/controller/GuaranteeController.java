package ingue.kakaopay.housingfinance.guarantee.controller;

import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.response.GetTotalGuaranteeByYearResponse;
import ingue.kakaopay.housingfinance.guarantee.service.GuaranteeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Guarantee 관련 api 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
public class GuaranteeController {

  private final GuaranteeService guaranteeService;

  @GetMapping(value = "/totalguarantees")
  public ResponseEntity getTotalGuaranteeByYear() {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = guaranteeService
        .findTotalGuaranteeByYear();

    GetTotalGuaranteeByYearResponse getTotalGuaranteeByYearResponse = GetTotalGuaranteeByYearResponse
        .create(totalGuaranteeByYearList);

    return ResponseEntity.ok(getTotalGuaranteeByYearResponse);
  }
}
