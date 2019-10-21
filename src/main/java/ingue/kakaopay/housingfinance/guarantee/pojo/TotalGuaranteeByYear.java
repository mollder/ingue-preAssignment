package ingue.kakaopay.housingfinance.guarantee.pojo;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 년도별 각 금융기관의 지원금액 합계 api를 나타내는 클래스
 */
@Getter
public class TotalGuaranteeByYear {

  private int year;
  private int totalAmount;
  private List<InstitutionMoney> detailAmount;

  /*
  생성자

  객체가 생성할 때 받은 정보를 넣어줌
   */
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

  /**
   * 돈을 받아서 totalAmount 값을 늘려주는 메소드
   * @param money
   */
  private void addTotalAmount(int money) {
    totalAmount += money;
  }

  /**
   * 금융기관, 돈을 파라미터로 받아서
   *
   * 만약 이미 객체안에 금융기관이 있다면
   * 그 금융기관의 돈을 늘려주고
   *
   * 없다면 새로운 금융기관 객체를 만들어서
   * 리스트에 삽입해주는 메소드
   *
   * @param institutionName 금융기관 이름
   * @param money 돈
   */
  private void addDetailAmount(String institutionName, int money) {
    if(isInstitutionExist(institutionName)) {
      InstitutionMoney institutionMoney = getInstitutionMoneyByName(institutionName);
      institutionMoney.addMoney(money);
    }else {
      InstitutionMoney institutionMoney = InstitutionMoney.create(institutionName, money);
      detailAmount.add(institutionMoney);
    }
  }

  /**
   * 현재 객체 안에
   *
   * 파라미터로 받은 금융기관이름과 같은 이름을 가진
   * 객체가 있는지 확인해주는 메소드
   *
   * @param institutionName 금융기관 이름
   * @return 같은 이름이 있는지 여부
   */
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

  /**
   * 금융기관 이름을 받아서
   * 만약 리스트 내에 금융기관 객체가 있다면
   * 돌려주는 메소드
   *
   * 로직에서는 금융기관이 있는지 확인하고 이 메소드를 호출해야 한다.
   * @param institutionName 금융기관이름
   * @return 금융기관 객체
   */
  private InstitutionMoney getInstitutionMoneyByName(String institutionName) {
    for(InstitutionMoney institutionMoney : detailAmount) {
      String name = institutionMoney.getInstitutionName();

      if(name.equals(institutionName)) return institutionMoney;
    }

    return InstitutionMoney.create("오류", 0);
  }

  /**
   * 금융기관 이름, 돈을 받아서
   * totalAmount, detailAmount 값을 각각 늘려주는
   * 메소드를 호출해주는 메소드
   *
   * @param institutionName 금융기관 이름
   * @param money 돈
   */
  public void addAmount(String institutionName, int money) {
    addTotalAmount(money);
    addDetailAmount(institutionName, money);
  }
}