package ingue.kakaopay.housingfinance.guarantee.repository;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import java.util.List;

/**
 * querydsl 관련 인터페이스
 */
public interface GuaranteeRepositoryCustom {

  List<Guarantee> findAllInnerJoinWithInstitutionOrderByYear();
}
