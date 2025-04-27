package com.mjutarzan.tarzan.domain.map.entity.shopping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMart is a Querydsl query type for Mart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMart extends EntityPathBase<Mart> {

    private static final long serialVersionUID = -366028030L;

    public static final QMart mart = new QMart("mart");

    public final QShopping _super = new QShopping(this);

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

    //inherited
    public final StringPath zipCode = _super.zipCode;

    public QMart(String variable) {
        super(Mart.class, forVariable(variable));
    }

    public QMart(Path<? extends Mart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMart(PathMetadata metadata) {
        super(Mart.class, metadata);
    }

}

