package ingue.kakaopay.housingfinance.guarantee.controller;

import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.MinAndMaxAvgGuaranteesByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.response.GetTotalGuaranteeByYearResponse;
import ingue.kakaopay.housingfinance.guarantee.service.GuaranteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class GuaranteeController {

  private final GuaranteeService guaranteeService;

  @GetMapping(value = "/total-guarantee")
  public GetTotalGuaranteeByYearResponse getTotalGuaranteeByYear() {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = guaranteeService
        .findTotalGuaranteeByYear();

    return GetTotalGuaranteeByYearResponse.create(totalGuaranteeByYearList);
  }

  @GetMapping(value = "/largest-guarantee")
  public LargestGuaranteeInstitutionByYear getLargestGuaranteeInstitutionByYear() {
    return guaranteeService.getLargestGuaranteeInstitutionByYear();
  }

  @GetMapping(value = "/minmax-guarantee")
  public MinAndMaxAvgGuaranteesByYear getMinMaxAvgKoreanExchangeBankFrom2005To2016() {
    return guaranteeService.minAndMaxAvgForeignExchangeBankFrom2005To2016();
  }
}
