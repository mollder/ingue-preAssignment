package ingue.kakaopay.housingfinance.common.csv.pojo;

import lombok.Getter;

@Getter
public enum OrderBy {
  ASCE("asce"),
  DESC("desc");

  private String type;

  OrderBy(String type) {
    this.type = type;
  }
}
