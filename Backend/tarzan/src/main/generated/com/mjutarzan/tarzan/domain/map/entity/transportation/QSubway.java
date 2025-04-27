package com.mjutarzan.tarzan.domain.map.entity.transportation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubway is a Querydsl query type for Subway
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubway extends EntityPathBase<Subway> {

    private static final long serialVersionUID = 648331479L;

    public static final QSubway subway = new QSubway("subway");

    public final QTransportation _super = new QTransportation(this);

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

    public final StringPath type = createString("type");

    public QSubway(String variable) {
        super(Subway.class, forVariable(variable));
    }

    public QSubway(Path<? extends Subway> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubway(PathMetadata metadata) {
        super(Subway.class, metadata);
    }

}

