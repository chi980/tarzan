package com.mjutarzan.tarzan.domain.map.entity.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPolice is a Querydsl query type for Police
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPolice extends EntityPathBase<Police> {

    private static final long serialVersionUID = 1673057650L;

    public static final QPolice police = new QPolice("police");

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

    public QPolice(String variable) {
        super(Police.class, forVariable(variable));
    }

    public QPolice(Path<? extends Police> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPolice(PathMetadata metadata) {
        super(Police.class, metadata);
    }

}

