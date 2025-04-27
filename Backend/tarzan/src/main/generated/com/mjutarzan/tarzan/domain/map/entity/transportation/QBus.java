package com.mjutarzan.tarzan.domain.map.entity.transportation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBus is a Querydsl query type for Bus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBus extends EntityPathBase<Bus> {

    private static final long serialVersionUID = -1372925096L;

    public static final QBus bus = new QBus("bus");

    public final QTransportation _super = new QTransportation(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ComparablePath<org.locationtech.jts.geom.Point> location = _super.location;

    public final StringPath mobileNumber = createString("mobileNumber");

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public final StringPath uniqueNumber = createString("uniqueNumber");

    public QBus(String variable) {
        super(Bus.class, forVariable(variable));
    }

    public QBus(Path<? extends Bus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBus(PathMetadata metadata) {
        super(Bus.class, metadata);
    }

}

