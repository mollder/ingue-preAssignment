package ingue.kakaopay.housingfinance.guarantee.repository;

import static ingue.kakaopay.housingfinance.guarantee.domain.QGuarantee.guarantee;
import static ingue.kakaopay.housingfinance.institution.domain.QInstitution.institution;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ingue.kakaopay.housingfinance.common.csv.pojo.OrderBy;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * querydsl 구현 클래스
 */
@RequiredArgsConstructor
public class GuaranteeRepositoryImpl implements GuaranteeRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  /**
   * DB에 저장된 Guarantee 객체중 Institution과 innerjoin된 객체들을 모두 가져오는 메소드 innerjoin을 하면서 fetch join을
   * 사용하였다.
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

  /**
   * 특정년도사이 특정 금융기관 총 금액을
   * 년도별로 계산해서 가장 작은 년도의 금액을
   * 돌려주는 메소드
   *
   * @param institutionName 기관 이름
   * @param start 시작 년도
   * @param end 종료 년도
   * @return 시작년도부터 종료년도까지 가장 작은 금액 평균
   */
  @Override
  public AvgAmountByYear findMinAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end) {
    return findAvgAmountByInstitutionNameBetweenYearOrderBy(institutionName, start, end,
        OrderBy.ASCE);
  }

  /**
   * 특정년도사이 특정 금융기관 총 금액을
   * 년도별로 계산해서 가장 큰 년도의 금액을
   * 돌려주는 메소드
   *
   * @param institutionName 기관 이름
   * @param start 시작 년도
   * @param end 종료 년도
   * @return 시작년도부터 종료년도까지 가장 큰 금액 평균
   */
  @Override
  public AvgAmountByYear findMaxAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end) {
    return findAvgAmountByInstitutionNameBetweenYearOrderBy(institutionName, start, end,
        OrderBy.DESC);
  }

  /**
   *  특정년도사이 특정 금융기관 총 금액을
   *  년도별로 계산해서 orderBy 값에 따라서
   *  가장 큰 금액, 가장 작은 금액을
   *  돌려주는 메소드
   *
   * @param institutionName 기관 이름
   * @param start 시작 년도
   * @param end 끝 년도
   * @param orderBy 작은값, 큰값 여부
   * @return orderBy에 따라 최솟값, 최댓값
   */
  private AvgAmountByYear findAvgAmountByInstitutionNameBetweenYearOrderBy(String institutionName,
      int start, int end, OrderBy orderBy) {
    JPAQuery<AvgAmountByYear> jpaQuery = queryFactory.selectFrom(guarantee)
        .innerJoin(guarantee.institution, institution)
        .where(institution.name.contains(institutionName), guarantee.year.between(start, end))
        .groupBy(guarantee.year)
        .select(Projections.constructor(AvgAmountByYear.class, guarantee.year,
            guarantee.money.avg().round().as("amount")))
        .limit(1);

    if (OrderBy.ASCE == orderBy) {
      jpaQuery = jpaQuery.orderBy(guarantee.money.avg().round().asc());
    } else {
      jpaQuery = jpaQuery.orderBy(guarantee.money.avg().round().desc());
    }

    return jpaQuery.fetchOne();
  }
}