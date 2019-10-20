package ingue.kakaopay.housingfinance.guarantee.repository;

import static ingue.kakaopay.housingfinance.guarantee.domain.QGuarantee.guarantee;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuaranteeRepositoryImpl implements GuaranteeRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Guarantee> findAll() {
    return queryFactory.selectFrom(guarantee)
        .fetch();
  }
}
