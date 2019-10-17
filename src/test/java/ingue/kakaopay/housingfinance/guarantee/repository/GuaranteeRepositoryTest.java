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
  public void institution_삽입_테스트() {
    Guarantee guarantee = new Guarantee(2015, 2, 2540);

    guaranteeRepository.save(guarantee);

    assertThat(guaranteeRepository.count()).isEqualTo(1);
  }

}
