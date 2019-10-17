package ingue.kakaopay.housingfinance.guarantee.repository;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuaranteeRepository extends JpaRepository<Guarantee, Long> {

}
