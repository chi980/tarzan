package com.mjutarzan.tarzan.domain.map.entity.clinic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPharmacy is a Querydsl query type for Pharmacy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPharmacy extends EntityPathBase<Pharmacy> {

    private static final long serialVersionUID = 1242974431L;

    public static final QPharmacy pharmacy = new QPharmacy("pharmacy");

    public final QClinic _super = new QClinic(this);

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

    public QPharmacy(String variable) {
        super(Pharmacy.class, forVariable(variable));
    }

    public QPharmacy(Path<? extends Pharmacy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPharmacy(PathMetadata metadata) {
        super(Pharmacy.class, metadata);
    }

}

