package ingue.kakaopay.housingfinance.guarantee.controller;

import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
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

  /**
   * 년도별 지원금액 리스트를 가져오는 api
   *
   * @return 년도별 지원금액 리스트
   */
  @GetMapping(value = "/totalguarantees")
  public ResponseEntity getTotalGuaranteeByYear() {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = guaranteeService
        .findTotalGuaranteeByYear();

    GetTotalGuaranteeByYearResponse getTotalGuaranteeByYearResponse = GetTotalGuaranteeByYearResponse
        .create(totalGuaranteeByYearList);

    return ResponseEntity.ok(getTotalGuaranteeByYearResponse);
  }

  /**
   * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명과 그해 년도를 조회하는 api
   *
   * @return 가장 큰 금액을 가진 기관명과 그해 년도
   */
  @GetMapping(value = "/largestguarantee")
  public ResponseEntity getLargestGuaranteeInstitutionByYear() {
    LargestGuaranteeInstitutionByYear largestGuaranteeInstitutionByYear = guaranteeService
        .getLargestGuaranteeInstitutionByYear();

    return ResponseEntity.ok(largestGuaranteeInstitutionByYear);
  }
}
