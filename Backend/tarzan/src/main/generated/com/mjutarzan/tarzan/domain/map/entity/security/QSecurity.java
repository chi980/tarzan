package com.mjutarzan.tarzan.domain.map.entity.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSecurity is a Querydsl query type for Security
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecurity extends EntityPathBase<Security> {

    private static final long serialVersionUID = 1892868756L;

    public static final QSecurity security = new QSecurity("security");

    public final com.mjutarzan.tarzan.domain.map.entity.QBuilding _super = new com.mjutarzan.tarzan.domain.map.entity.QBuilding(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    public final EnumPath<com.mjutarzan.tarzan.global.common.vo.SiGunGu> gu = createEnum("gu", com.mjutarzan.tarzan.global.common.vo.SiGunGu.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public QSecurity(String variable) {
        super(Security.class, forVariable(variable));
    }

    public QSecurity(Path<? extends Security> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSecurity(PathMetadata metadata) {
        super(Security.class, metadata);
    }

}

