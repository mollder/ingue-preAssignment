package ingue.kakaopay.housingfinance.guarantee.repository;

import ingue.kakaopay.housingfinance.common.csv.pojo.OrderBy;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;
import java.util.List;

/**
 * querydsl 관련 인터페이스
 */
public interface GuaranteeRepositoryCustom {

  List<Guarantee> findAllInnerJoinWithInstitutionOrderByYear();

  AvgAmountByYear findMaxAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end);

  AvgAmountByYear findMinAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end);
}
