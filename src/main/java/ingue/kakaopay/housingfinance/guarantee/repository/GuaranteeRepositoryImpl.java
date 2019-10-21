package ingue.kakaopay.housingfinance.guarantee.repository;

import static ingue.kakaopay.housingfinance.guarantee.domain.QGuarantee.guarantee;
import static ingue.kakaopay.housingfinance.institution.domain.QInstitution.institution;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * querydsl 구현 클래스
 */
@RequiredArgsConstructor
public class GuaranteeRepositoryImpl implements GuaranteeRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  /**
   * DB에 저장된 Guarantee 객체중 Institution과 innerjoin된 객체들을 모두 가져오는 메소드
   * innerjoin을 하면서 fetch join을 사용하였다.
   *
   * @return Guarantee, Institution inner join 결과
   */
  @Override
  public List<Guarantee> findAllInnerJoinWithInstitutionOrderByYear() {
    return queryFactory.selectFrom(guarantee)
        .innerJoin(guarantee.institution, institution)
        .fetchJoin()
        .orderBy(guarantee.year.asc())
        .fetch();
  }
}