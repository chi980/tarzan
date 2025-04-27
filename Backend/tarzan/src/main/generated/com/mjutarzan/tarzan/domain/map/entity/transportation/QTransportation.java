package com.mjutarzan.tarzan.domain.map.entity.transportation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransportation is a Querydsl query type for Transportation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransportation extends EntityPathBase<Transportation> {

    private static final long serialVersionUID = 1231096212L;

    public static final QTransportation transportation = new QTransportation("transportation");

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

    public QTransportation(String variable) {
        super(Transportation.class, forVariable(variable));
    }

    public QTransportation(Path<? extends Transportation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransportation(PathMetadata metadata) {
        super(Transportation.class, metadata);
    }

}

