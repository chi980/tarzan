package com.mjutarzan.tarzan.domain.map.entity.amenity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCivicCenter is a Querydsl query type for CivicCenter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCivicCenter extends EntityPathBase<CivicCenter> {

    private static final long serialVersionUID = 338281936L;

    public static final QCivicCenter civicCenter = new QCivicCenter("civicCenter");

    public final QAmenity _super = new QAmenity(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    public final StringPath dong = createString("dong");

    public final EnumPath<com.mjutarzan.tarzan.global.common.vo.SiGunGu> gu = createEnum("gu", com.mjutarzan.tarzan.global.common.vo.SiGunGu.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public QCivicCenter(String variable) {
        super(CivicCenter.class, forVariable(variable));
    }

    public QCivicCenter(Path<? extends CivicCenter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCivicCenter(PathMetadata metadata) {
        super(CivicCenter.class, metadata);
    }

}

