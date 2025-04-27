package com.mjutarzan.tarzan.domain.map.entity.shopping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConvenienceStore is a Querydsl query type for ConvenienceStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConvenienceStore extends EntityPathBase<ConvenienceStore> {

    private static final long serialVersionUID = -167353514L;

    public static final QConvenienceStore convenienceStore = new QConvenienceStore("convenienceStore");

    public final QShopping _super = new QShopping(this);

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

    //inherited
    public final StringPath zipCode = _super.zipCode;

    public QConvenienceStore(String variable) {
        super(ConvenienceStore.class, forVariable(variable));
    }

    public QConvenienceStore(Path<? extends ConvenienceStore> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConvenienceStore(PathMetadata metadata) {
        super(ConvenienceStore.class, metadata);
    }

}

