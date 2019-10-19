package ingue.kakaopay.housingfinance.common.csv.vo;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.List;
import lombok.Getter;

/**
 * Csv 파일 내용을 Guarantee, Institution 객체로 변환 뒤
 * 객체들을 저장할 VO 객체
 */
@Getter
public class CsvVO {

  private List<Guarantee> guaranteeList;
  private List<Institution> institutionList;

  public CsvVO(List<Guarantee> guaranteeList, List<Institution> institutionList) {
    this.guaranteeList = guaranteeList;
    this.institutionList = institutionList;
  }
}