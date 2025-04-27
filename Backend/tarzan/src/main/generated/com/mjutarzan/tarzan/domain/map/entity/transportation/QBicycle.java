package com.mjutarzan.tarzan.domain.map.entity.transportation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBicycle is a Querydsl query type for Bicycle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBicycle extends EntityPathBase<Bicycle> {

    private static final long serialVersionUID = 373180887L;

    public static final QBicycle bicycle = new QBicycle("bicycle");

    public final QTransportation _super = new QTransportation(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    public final EnumPath<com.mjutarzan.tarzan.global.common.vo.SiGunGu> gu = createEnum("gu", com.mjutarzan.tarzan.global.common.vo.SiGunGu.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Double> lcdCnt = createNumber("lcdCnt", Double.class);

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    //inherited
    public final StringPath name = _super.name;

    public final EnumPath<com.mjutarzan.tarzan.domain.map.model.vo.BicycleOperType> operType = createEnum("operType", com.mjutarzan.tarzan.domain.map.model.vo.BicycleOperType.class);

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public final NumberPath<Double> qrCnt = createNumber("qrCnt", Double.class);

    public final NumberPath<Integer> uniqueNumber = createNumber("uniqueNumber", Integer.class);

    public QBicycle(String variable) {
        super(Bicycle.class, forVariable(variable));
    }

    public QBicycle(Path<? extends Bicycle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBicycle(PathMetadata metadata) {
        super(Bicycle.class, metadata);
    }

}

