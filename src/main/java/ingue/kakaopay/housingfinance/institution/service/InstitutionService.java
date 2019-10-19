package ingue.kakaopay.housingfinance.institution.service;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

  private final InstitutionRepository institutionRepository;

  public List<Institution> saveAll(List<Institution> institutionList) {
    return this.institutionRepository.saveAll(institutionList);
  }
}
