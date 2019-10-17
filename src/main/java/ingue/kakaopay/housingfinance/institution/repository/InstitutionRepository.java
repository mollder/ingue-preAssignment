package ingue.kakaopay.housingfinance.institution.repository;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}
