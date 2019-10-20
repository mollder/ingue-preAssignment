package ingue.kakaopay.housingfinance.guarantee.repository;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import java.util.List;

public interface GuaranteeRepositoryCustom {

  List<Guarantee> findAll();
}
