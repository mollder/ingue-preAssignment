package ingue.kakaopay.housingfinance.guarantee.service;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.InstitutionMoney;
import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Guarantee 관련 비즈니스 로직 클래스
 */
@Service
@RequiredArgsConstructor
public class GuaranteeService {

  private final GuaranteeRepository guaranteeRepository;

  /**
   * Guarantee 객체 리스트를 받아서 repository에 모두 삽입하는 메소드를 호출하는 메소드
   *
   * @param guaranteeList Guarantee 객체 리스트
   * @return 삽입한 결과
   */
  public List<Guarantee> saveAll(List<Guarantee> guaranteeList) {
    return guaranteeRepository.saveAll(guaranteeList);
  }

  /**
   * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관을 돌려주는 메소드
   *
   * @return 각 년도별 지원금액 중에서 가장 큰 금액의 기관
   */
  public LargestGuaranteeInstitutionByYear getLargestGuaranteeInstitutionByYear() {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = findTotalGuaranteeByYear();

    if (totalGuaranteeByYearList.size() == 0) {
      throw new RuntimeException("no data in db");
    }

    int year = 0;
    int money = Integer.MIN_VALUE;
    String bankName = "";

    for (TotalGuaranteeByYear totalGuaranteeByYear : totalGuaranteeByYearList) {
      for (InstitutionMoney institutionMoney : totalGuaranteeByYear.getDetailAmount()) {
        if (money < institutionMoney.getMoney()) {
          year = totalGuaranteeByYear.getYear();
          money = institutionMoney.getMoney();
          bankName = institutionMoney.getInstitutionName();
        }
      }
    }

    return LargestGuaranteeInstitutionByYear.create(year, bankName);
  }

  /**
   * Guarantee 객체들을 받아서 년도별로 총 지원 금액, 은행별 개별 지원 금액을 계산해서 돌려주는 메소드
   *
   * @return 년도별 지원 금액 리스트
   */
  public List<TotalGuaranteeByYear> findTotalGuaranteeByYear() {
    List<Guarantee> guaranteeList = guaranteeRepository
        .findAllInnerJoinWithInstitutionOrderByYear();
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = new ArrayList<>();

    for (Guarantee guarantee : guaranteeList) {
      int year = guarantee.getYear();
      int money = guarantee.getMoney();

      Institution institution = guarantee.getInstitution();
      String institutionName = institution.getName();

      if (isExistYear(year, totalGuaranteeByYearList)) {
        TotalGuaranteeByYear totalGuaranteeByYear = getTotalGuaranteeUseYear(year,
            totalGuaranteeByYearList);

        totalGuaranteeByYear.addAmount(institutionName, money);
      } else {
        TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear
            .create(year, money, institutionName);
        totalGuaranteeByYearList.add(totalGuaranteeByYear);
      }
    }

    return totalGuaranteeByYearList;
  }

  /**
   * 파라미터로 받은 리스트에 파라미터로 받은 년도와 같은 년도를 가진 객체가 있다면 돌려주는 메소드
   *
   * @param year                     비교할년도
   * @param totalGuaranteeByYearList 년도별 합계 보증 리스트
   * @return 같은 년도를 가진 TotalGuaranteeByYear 객체
   */
  private TotalGuaranteeByYear getTotalGuaranteeUseYear(int year,
      List<TotalGuaranteeByYear> totalGuaranteeByYearList) {
    for (TotalGuaranteeByYear totalGuaranteeByYear : totalGuaranteeByYearList) {
      if (totalGuaranteeByYear.getYear() == year) {
        return totalGuaranteeByYear;
      }
    }

    return TotalGuaranteeByYear.create(0, 0, "오류");
  }

  /**
   * TotalGuaranteeByYear 리스트 안에 인자로 받은 년도와 같은 년도를 가진 객체가 있는지 확인해주는 메소드
   *
   * @param year                     년도
   * @param totalGuaranteeByYearList 년도별 합계 보증 객체리스트
   * @return 리스트 안에 같은 년도가 있는지 여부
   */
  private boolean isExistYear(int year,
      List<TotalGuaranteeByYear> totalGuaranteeByYearList) {
    boolean exist = false;

    for (TotalGuaranteeByYear totalGuaranteeByYear : totalGuaranteeByYearList) {
      if (totalGuaranteeByYear.getYear() == year) {
        exist = true;
        break;
      }
    }

    return exist;
  }
}