package com.mjutarzan.tarzan.domain.house.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHouse is a Querydsl query type for UserHouse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserHouse extends EntityPathBase<UserHouse> {

    private static final long serialVersionUID = -398014665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHouse userHouse = new QUserHouse("userHouse");

    public final QHouse _super = new QHouse(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final ListPath<com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark> bookmarkList = _super.bookmarkList;

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    public final com.mjutarzan.tarzan.domain.user.entity.QUser register;

    //inherited
    public final ListPath<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview> reviewList = _super.reviewList;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUserHouse(String variable) {
        this(UserHouse.class, forVariable(variable), INITS);
    }

    public QUserHouse(Path<? extends UserHouse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHouse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHouse(PathMetadata metadata, PathInits inits) {
        this(UserHouse.class, metadata, inits);
    }

    public QUserHouse(Class<? extends UserHouse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.register = inits.isInitialized("register") ? new com.mjutarzan.tarzan.domain.user.entity.QUser(forProperty("register")) : null;
    }

}

