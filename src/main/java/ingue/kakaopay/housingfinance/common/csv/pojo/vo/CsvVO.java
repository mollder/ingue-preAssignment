package ingue.kakaopay.housingfinance.common.csv.pojo.vo;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.List;
import lombok.Getter;

@Getter
public class CsvVO {

  private List<Guarantee> guaranteeList;
  private List<Institution> institutionList;

  public CsvVO(List<Guarantee> guaranteeList, List<Institution> institutionList) {
    this.guaranteeList = guaranteeList;
    this.institutionList = institutionList;
  }
}