package ingue.kakaopay.housingfinance.guarantee.service;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuaranteeService {

  private final GuaranteeRepository guaranteeRepository;

  public List<Guarantee> saveAll(List<Guarantee> guaranteeList) {
    return this.guaranteeRepository.saveAll(guaranteeList);
  }
}
