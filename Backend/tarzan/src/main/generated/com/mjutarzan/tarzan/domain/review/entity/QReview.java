package com.mjutarzan.tarzan.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1386641420L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.mjutarzan.tarzan.global.common.entity.QCommonEntity _super = new com.mjutarzan.tarzan.global.common.entity.QCommonEntity(this);

    public final StringPath advantage = createString("advantage");

    public final ListPath<String, StringPath> advantageTagList = this.<String, StringPath>createList("advantageTagList", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> deposit = createNumber("deposit", Integer.class);

    public final StringPath disadvantage = createString("disadvantage");

    public final ListPath<String, StringPath> disadvantageTagList = this.<String, StringPath>createList("disadvantageTagList", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final com.mjutarzan.tarzan.domain.house.entity.QHouse house;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final EnumPath<com.mjutarzan.tarzan.domain.review.model.vo.LeaseType> leaseType = createEnum("leaseType", com.mjutarzan.tarzan.domain.review.model.vo.LeaseType.class);

    public final NumberPath<Integer> managementFee = createNumber("managementFee", Integer.class);

    public final BooleanPath optionBug = createBoolean("optionBug");

    public final BooleanPath optionBuildingManagement = createBoolean("optionBuildingManagement");

    public final BooleanPath optionElevator = createBoolean("optionElevator");

    public final BooleanPath optionFlat = createBoolean("optionFlat");

    public final BooleanPath optionMold = createBoolean("optionMold");

    public final BooleanPath optionNone = createBoolean("optionNone");

    public final BooleanPath optionParking = createBoolean("optionParking");

    public final BooleanPath optionPet = createBoolean("optionPet");

    public final BooleanPath optionPreventHeat = createBoolean("optionPreventHeat");

    public final BooleanPath optionQuite = createBoolean("optionQuite");

    public final BooleanPath optionSecurity = createBoolean("optionSecurity");

    public final BooleanPath optionSecurityGuard = createBoolean("optionSecurityGuard");

    public final BooleanPath optionSoundProof = createBoolean("optionSoundProof");

    public final BooleanPath optionTrail = createBoolean("optionTrail");

    public final BooleanPath optionVentilation = createBoolean("optionVentilation");

    public final NumberPath<Integer> rent = createNumber("rent", Integer.class);

    public final NumberPath<Integer> residencePeriod = createNumber("residencePeriod", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.mjutarzan.tarzan.domain.user.entity.QUser writer;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.house = inits.isInitialized("house") ? new com.mjutarzan.tarzan.domain.house.entity.QHouse(forProperty("house")) : null;
        this.writer = inits.isInitialized("writer") ? new com.mjutarzan.tarzan.domain.user.entity.QUser(forProperty("writer")) : null;
    }

}

