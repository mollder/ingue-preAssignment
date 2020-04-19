package ingue.kakaopay.housingfinance.guarantee.pojo;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TotalGuaranteeByYear {

  private int year;
  private int totalAmount;
  private List<InstitutionMoney> detailAmount;

  private TotalGuaranteeByYear(int year, int totalAmount, String institutionName) {
    this.year = year;
    this.totalAmount = totalAmount;
    detailAmount = new ArrayList<>();

    InstitutionMoney institutionMoney = InstitutionMoney.create(institutionName, totalAmount);
    detailAmount.add(institutionMoney);
  }

  public static TotalGuaranteeByYear create(int year, int totalAmount, String institutionName) {
    return new TotalGuaranteeByYear(year, totalAmount, institutionName);
  }

  private void addTotalAmount(int money) {
    totalAmount += money;
  }

  private void addDetailAmount(String institutionName, int money) {
    if(isInstitutionExist(institutionName)) {
      InstitutionMoney institutionMoney = getInstitutionMoneyByName(institutionName);
      institutionMoney.addMoney(money);
    }else {
      InstitutionMoney institutionMoney = InstitutionMoney.create(institutionName, money);
      detailAmount.add(institutionMoney);
    }
  }

  private boolean isInstitutionExist(String institutionName) {
    boolean isExist = false;

    for(InstitutionMoney institutionMoney : detailAmount) {
      String name = institutionMoney.getInstitutionName();
      if(name.equals(institutionName)) {
        isExist = true;
        break;
      }
    }

    return isExist;
  }

  private InstitutionMoney getInstitutionMoneyByName(String institutionName) {
    for(InstitutionMoney institutionMoney : detailAmount) {
      String name = institutionMoney.getInstitutionName();

      if(name.equals(institutionName)) return institutionMoney;
    }

    return InstitutionMoney.create("오류", 0);
  }

  public void addAmount(String institutionName, int money) {
    addTotalAmount(money);
    addDetailAmount(institutionName, money);
  }
}