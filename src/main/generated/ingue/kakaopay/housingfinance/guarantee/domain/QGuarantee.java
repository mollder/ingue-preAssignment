package ingue.kakaopay.housingfinance.guarantee.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuarantee is a Querydsl query type for Guarantee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGuarantee extends EntityPathBase<Guarantee> {

    private static final long serialVersionUID = -894231704L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuarantee guarantee = new QGuarantee("guarantee");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ingue.kakaopay.housingfinance.institution.domain.QInstitution institution;

    public final NumberPath<Integer> money = createNumber("money", Integer.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QGuarantee(String variable) {
        this(Guarantee.class, forVariable(variable), INITS);
    }

    public QGuarantee(Path<? extends Guarantee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuarantee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuarantee(PathMetadata metadata, PathInits inits) {
        this(Guarantee.class, metadata, inits);
    }

    public QGuarantee(Class<? extends Guarantee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.institution = inits.isInitialized("institution") ? new ingue.kakaopay.housingfinance.institution.domain.QInstitution(forProperty("institution")) : null;
    }

}

