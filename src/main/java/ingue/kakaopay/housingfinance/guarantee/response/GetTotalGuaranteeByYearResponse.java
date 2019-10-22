package ingue.kakaopay.housingfinance.guarantee.response;

import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import java.util.List;
import lombok.Getter;

/**
 * 년도별 각 금융기관의 지원금액 합계 api response 객체
 *
 * api 결과를 이 객체로 씌운뒤에 돌려준다.
 */
@Getter
public class GetTotalGuaranteeByYearResponse {

  private String name;
  private List<TotalGuaranteeByYear> totalGuaranteeByYearList;

  private GetTotalGuaranteeByYearResponse(List<TotalGuaranteeByYear> totalGuaranteeByYearList) {
    name = "년도별 각 금융기관 지원금액 합계";
    this.totalGuaranteeByYearList = totalGuaranteeByYearList;
  }

  public static GetTotalGuaranteeByYearResponse create(List<TotalGuaranteeByYear> totalGuaranteeByYearList) {
    return new GetTotalGuaranteeByYearResponse(totalGuaranteeByYearList);
  }
}