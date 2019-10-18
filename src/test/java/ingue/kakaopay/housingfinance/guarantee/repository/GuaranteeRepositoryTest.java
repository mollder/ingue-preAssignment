package ingue.kakaopay.housingfinance.guarantee.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuaranteeRepositoryTest {

  @Autowired
  private GuaranteeRepository guaranteeRepository;

  @Before
  public void setUp() {
    guaranteeRepository.deleteAll();
  }

  @Test
  public void Guarantee_객체하나삽입하면_사이즈1() {
    Guarantee guarantee = Guarantee.builder()
        .year(2015)
        .month(2)
        .money(2540)
        .build();

    guaranteeRepository.save(guarantee);

    assertThat(guaranteeRepository.count()).isEqualTo(1);
  }

}
