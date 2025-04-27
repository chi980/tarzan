package com.mjutarzan.tarzan.domain.house.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QApiHouse is a Querydsl query type for ApiHouse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApiHouse extends EntityPathBase<ApiHouse> {

    private static final long serialVersionUID = 1200044996L;

    public static final QApiHouse apiHouse = new QApiHouse("apiHouse");

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

    //inherited
    public final ListPath<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview> reviewList = _super.reviewList;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QApiHouse(String variable) {
        super(ApiHouse.class, forVariable(variable));
    }

    public QApiHouse(Path<? extends ApiHouse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApiHouse(PathMetadata metadata) {
        super(ApiHouse.class, metadata);
    }

}

