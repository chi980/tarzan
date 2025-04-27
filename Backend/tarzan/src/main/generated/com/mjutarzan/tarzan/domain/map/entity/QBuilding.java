package com.mjutarzan.tarzan.domain.map.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBuilding is a Querydsl query type for Building
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuilding extends EntityPathBase<Building> {

    private static final long serialVersionUID = 1840843726L;

    public static final QBuilding building = new QBuilding("building");

    public final StringPath address = createString("address");

    public final StringPath category = createString("category");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<org.locationtech.jts.geom.Point> location = createComparable("location", org.locationtech.jts.geom.Point.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QBuilding(String variable) {
        super(Building.class, forVariable(variable));
    }

    public QBuilding(Path<? extends Building> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBuilding(PathMetadata metadata) {
        super(Building.class, metadata);
    }

}

