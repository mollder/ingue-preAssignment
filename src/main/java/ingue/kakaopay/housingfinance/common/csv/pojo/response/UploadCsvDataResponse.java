package ingue.kakaopay.housingfinance.common.csv.pojo.response;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.List;
import lombok.Getter;

/**
 * 금융 데이터 csv 파일 업로드 api 관련 response 객체
 */
@Getter
public class UploadCsvDataResponse {

  private String name;
  private List<Guarantee> guaranteeList;
  private List<Institution> institutionList;

  private UploadCsvDataResponse(List<Guarantee> guaranteeList, List<Institution> institutionList) {
    this.name = "데이터 파일 업로드 현황";
    this.guaranteeList = guaranteeList;
    this.institutionList = institutionList;
  }

  public static UploadCsvDataResponse create(List<Guarantee> guaranteeList,
      List<Institution> institutionList) {
    return new UploadCsvDataResponse(guaranteeList, institutionList);
  }
}
