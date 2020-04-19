package ingue.kakaopay.housingfinance.institution.response;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.List;
import lombok.Getter;

@Getter
public class FindAllInstitutionsResponse {

  private String name;
  private List<Institution> institutionList;

  private FindAllInstitutionsResponse(List<Institution> institutionList) {
    this.name = "주택금융 공급 금융기관(은행) 목록 현황";
    this.institutionList = institutionList;
  }

  public static FindAllInstitutionsResponse create(List<Institution> institutionList) {
    return new FindAllInstitutionsResponse(institutionList);
  }

}
