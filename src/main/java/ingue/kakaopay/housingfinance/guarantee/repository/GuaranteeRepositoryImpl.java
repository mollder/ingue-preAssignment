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

@RequiredArgsConstructor
public class GuaranteeRepositoryImpl implements GuaranteeRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Guarantee> findAllInnerJoinWithInstitutionOrderByYear() {
    return queryFactory.selectFrom(guarantee)
        .innerJoin(guarantee.institution, institution)
        .fetchJoin()
        .orderBy(guarantee.year.asc())
        .fetch();
  }

  @Override
  public AvgAmountByYear findMinAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end) {
    return findAvgAmountByInstitutionNameBetweenYearOrderBy(institutionName, start, end,
        OrderBy.ASCE);
  }

  @Override
  public AvgAmountByYear findMaxAvgAmountByInstitutionNameBetweenYear(String institutionName,
      int start, int end) {
    return findAvgAmountByInstitutionNameBetweenYearOrderBy(institutionName, start, end,
        OrderBy.DESC);
  }

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