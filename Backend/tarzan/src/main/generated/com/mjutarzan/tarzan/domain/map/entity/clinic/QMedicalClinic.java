package com.mjutarzan.tarzan.domain.map.entity.clinic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMedicalClinic is a Querydsl query type for MedicalClinic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMedicalClinic extends EntityPathBase<MedicalClinic> {

    private static final long serialVersionUID = -301735379L;

    public static final QMedicalClinic medicalClinic = new QMedicalClinic("medicalClinic");

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

    public QMedicalClinic(String variable) {
        super(MedicalClinic.class, forVariable(variable));
    }

    public QMedicalClinic(Path<? extends MedicalClinic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMedicalClinic(PathMetadata metadata) {
        super(MedicalClinic.class, metadata);
    }

}

