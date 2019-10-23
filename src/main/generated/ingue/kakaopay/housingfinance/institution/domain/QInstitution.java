package ingue.kakaopay.housingfinance.institution.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QInstitution is a Querydsl query type for Institution
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInstitution extends EntityPathBase<Institution> {

    private static final long serialVersionUID = 1025492072L;

    public static final QInstitution institution = new QInstitution("institution");

    public final NumberPath<Long> code = createNumber("code", Long.class);

    public final StringPath name = createString("name");

    public QInstitution(String variable) {
        super(Institution.class, forVariable(variable));
    }

    public QInstitution(Path<? extends Institution> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInstitution(PathMetadata metadata) {
        super(Institution.class, metadata);
    }

}

