package com.mjutarzan.tarzan.domain.map.entity.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCctv is a Querydsl query type for Cctv
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCctv extends EntityPathBase<Cctv> {

    private static final long serialVersionUID = 577878070L;

    public static final QCctv cctv = new QCctv("cctv");

    public final QSecurity _super = new QSecurity(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final EnumPath<com.mjutarzan.tarzan.global.common.vo.SiGunGu> gu = _super.gu;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public QCctv(String variable) {
        super(Cctv.class, forVariable(variable));
    }

    public QCctv(Path<? extends Cctv> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCctv(PathMetadata metadata) {
        super(Cctv.class, metadata);
    }

}

