package com.mjutarzan.tarzan.domain.map.entity.shopping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopping is a Querydsl query type for Shopping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopping extends EntityPathBase<Shopping> {

    private static final long serialVersionUID = 232116116L;

    public static final QShopping shopping = new QShopping("shopping");

    public final com.mjutarzan.tarzan.domain.map.entity.QBuilding _super = new com.mjutarzan.tarzan.domain.map.entity.QBuilding(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public final StringPath zipCode = createString("zipCode");

    public QShopping(String variable) {
        super(Shopping.class, forVariable(variable));
    }

    public QShopping(Path<? extends Shopping> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopping(PathMetadata metadata) {
        super(Shopping.class, metadata);
    }

}

