package com.mjutarzan.tarzan.domain.map.entity.amenity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPark is a Querydsl query type for Park
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPark extends EntityPathBase<Park> {

    private static final long serialVersionUID = -1956293095L;

    public static final QPark park = new QPark("park");

    public final QAmenity _super = new QAmenity(this);

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

    public QPark(String variable) {
        super(Park.class, forVariable(variable));
    }

    public QPark(Path<? extends Park> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPark(PathMetadata metadata) {
        super(Park.class, metadata);
    }

}

