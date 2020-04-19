package ingue.kakaopay.housingfinance.guarantee.controller;

import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.MinAndMaxAvgGuaranteesByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.response.GetTotalGuaranteeByYearResponse;
import ingue.kakaopay.housingfinance.guarantee.service.GuaranteeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class GuaranteeController {

  private final GuaranteeService guaranteeService;

  @GetMapping(value = "/total-guarantee")
  public ResponseEntity getTotalGuaranteeByYear() {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = guaranteeService
        .findTotalGuaranteeByYear();

    GetTotalGuaranteeByYearResponse getTotalGuaranteeByYearResponse = GetTotalGuaranteeByYearResponse
        .create(totalGuaranteeByYearList);

    return ResponseEntity.ok(getTotalGuaranteeByYearResponse);
  }

  @GetMapping(value = "/largest-guarantee")
  public ResponseEntity getLargestGuaranteeInstitutionByYear() {
    LargestGuaranteeInstitutionByYear largestGuaranteeInstitutionByYear = guaranteeService
        .getLargestGuaranteeInstitutionByYear();

    return ResponseEntity.ok(largestGuaranteeInstitutionByYear);
  }

  @GetMapping(value = "/minmax-guarantee")
  public ResponseEntity getMinMaxAvgKoreanExchangeBankFrom2005To2016() {
    MinAndMaxAvgGuaranteesByYear minAndMaxAvgGuaranteesByYear = guaranteeService
        .minAndMaxAvgForeignExchangeBankFrom2005To2016();

    return ResponseEntity.ok(minAndMaxAvgGuaranteesByYear);
  }
}
