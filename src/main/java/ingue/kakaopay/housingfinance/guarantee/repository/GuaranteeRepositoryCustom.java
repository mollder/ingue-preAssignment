package ingue.kakaopay.housingfinance.guarantee.repository;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;

import java.util.List;


public interface GuaranteeRepositoryCustom {

  List<Guarantee> findAllInnerJoinWithInstitutionOrderByYear();

  AvgAmountByYear findMaxAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end);

  AvgAmountByYear findMinAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end);
}
