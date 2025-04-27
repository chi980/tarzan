package com.mjutarzan.tarzan.domain.bookmark.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChecklistItem is a Querydsl query type for ChecklistItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChecklistItem extends EntityPathBase<ChecklistItem> {

    private static final long serialVersionUID = -2083479737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChecklistItem checklistItem = new QChecklistItem("checklistItem");

    public final com.mjutarzan.tarzan.global.common.entity.QCommonEntity _super = new com.mjutarzan.tarzan.global.common.entity.QCommonEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType> type = createEnum("type", com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.mjutarzan.tarzan.domain.user.entity.QUser user;

    public final BooleanPath value = createBoolean("value");

    public QChecklistItem(String variable) {
        this(ChecklistItem.class, forVariable(variable), INITS);
    }

    public QChecklistItem(Path<? extends ChecklistItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChecklistItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChecklistItem(PathMetadata metadata, PathInits inits) {
        this(ChecklistItem.class, metadata, inits);
    }

    public QChecklistItem(Class<? extends ChecklistItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.mjutarzan.tarzan.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

