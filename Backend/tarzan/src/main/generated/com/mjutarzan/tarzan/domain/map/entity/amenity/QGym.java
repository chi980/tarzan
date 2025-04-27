package com.mjutarzan.tarzan.domain.map.entity.amenity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGym is a Querydsl query type for Gym
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGym extends EntityPathBase<Gym> {

    private static final long serialVersionUID = -1864229460L;

    public static final QGym gym = new QGym("gym");

    public final QAmenity _super = new QAmenity(this);

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

    public QGym(String variable) {
        super(Gym.class, forVariable(variable));
    }

    public QGym(Path<? extends Gym> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGym(PathMetadata metadata) {
        super(Gym.class, metadata);
    }

}

