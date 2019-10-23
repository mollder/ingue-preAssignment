package ingue.kakaopay.housingfinance.guarantee.pojo;

import lombok.Getter;

@Getter
public class LargestGuaranteeInstitutionByYear {

  private int year;
  private String bank;

  private LargestGuaranteeInstitutionByYear(int year, String bank) {
    this.year = year;
    this.bank = bank;
  }

  public static LargestGuaranteeInstitutionByYear create(int year, String bank) {
    return new LargestGuaranteeInstitutionByYear(year, bank);
  }
}
