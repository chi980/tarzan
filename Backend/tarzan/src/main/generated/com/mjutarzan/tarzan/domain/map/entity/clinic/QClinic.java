package com.mjutarzan.tarzan.domain.map.entity.clinic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClinic is a Querydsl query type for Clinic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClinic extends EntityPathBase<Clinic> {

    private static final long serialVersionUID = -903269356L;

    public static final QClinic clinic = new QClinic("clinic");

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

    public QClinic(String variable) {
        super(Clinic.class, forVariable(variable));
    }

    public QClinic(Path<? extends Clinic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClinic(PathMetadata metadata) {
        super(Clinic.class, metadata);
    }

}

