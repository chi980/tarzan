package com.mjutarzan.tarzan.domain.house.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouse is a Querydsl query type for House
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouse extends EntityPathBase<House> {

    private static final long serialVersionUID = 962190658L;

    public static final QHouse house = new QHouse("house");

    public final com.mjutarzan.tarzan.global.common.entity.QCommonEntity _super = new com.mjutarzan.tarzan.global.common.entity.QCommonEntity(this);

    public final StringPath address = createString("address");

    public final ListPath<com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark> bookmarkList = this.<com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark>createList("bookmarkList", com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark.class, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<org.locationtech.jts.geom.Point> location = createComparable("location", org.locationtech.jts.geom.Point.class);

    public final StringPath name = createString("name");

    public final ListPath<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview> reviewList = this.<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview>createList("reviewList", com.mjutarzan.tarzan.domain.review.entity.Review.class, com.mjutarzan.tarzan.domain.review.entity.QReview.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QHouse(String variable) {
        super(House.class, forVariable(variable));
    }

    public QHouse(Path<? extends House> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHouse(PathMetadata metadata) {
        super(House.class, metadata);
    }

}

