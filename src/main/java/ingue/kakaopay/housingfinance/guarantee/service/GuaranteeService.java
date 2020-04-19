package ingue.kakaopay.housingfinance.guarantee.service;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.InstitutionMoney;
import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.MinAndMaxAvgGuaranteesByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuaranteeService {

  private final GuaranteeRepository guaranteeRepository;

  public List<Guarantee> saveAll(List<Guarantee> guaranteeList) {
    return guaranteeRepository.saveAll(guaranteeList);
  }

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

  private TotalGuaranteeByYear getTotalGuaranteeUseYear(int year,
      List<TotalGuaranteeByYear> totalGuaranteeByYearList) {
    for (TotalGuaranteeByYear totalGuaranteeByYear : totalGuaranteeByYearList) {
      if (totalGuaranteeByYear.getYear() == year) {
        return totalGuaranteeByYear;
      }
    }

    return TotalGuaranteeByYear.create(0, 0, "오류");
  }

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

  public MinAndMaxAvgGuaranteesByYear minAndMaxAvgForeignExchangeBankFrom2005To2016() {
    String institutionName = "외환은행";
    int startYear = 2005;
    int endYear = 2016;

    List<AvgAmountByYear> avgAmountByYearList = new ArrayList<>();

    AvgAmountByYear max = guaranteeRepository
        .findMaxAvgAmountByInstitutionNameBetweenYear(institutionName, startYear, endYear);
    AvgAmountByYear min = guaranteeRepository
        .findMinAvgAmountByInstitutionNameBetweenYear(institutionName, startYear, endYear);

    avgAmountByYearList.add(min);
    avgAmountByYearList.add(max);

    return MinAndMaxAvgGuaranteesByYear
        .create(institutionName, avgAmountByYearList);
  }
}