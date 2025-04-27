package com.mjutarzan.tarzan.domain.bookmark.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmark is a Querydsl query type for Bookmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmark extends EntityPathBase<Bookmark> {

    private static final long serialVersionUID = -183904504L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmark bookmark = new QBookmark("bookmark");

    public final com.mjutarzan.tarzan.global.common.entity.QCommonEntity _super = new com.mjutarzan.tarzan.global.common.entity.QCommonEntity(this);

    public final DatePath<java.time.LocalDate> availableMoveInDate = createDate("availableMoveInDate", java.time.LocalDate.class);

    public final NumberPath<Integer> bathRoomCnt = createNumber("bathRoomCnt", Integer.class);

    public final BooleanPath canAnimal = createBoolean("canAnimal");

    public final ListPath<BookmarkChecklistItem, QBookmarkChecklistItem> checkListItemList = this.<BookmarkChecklistItem, QBookmarkChecklistItem>createList("checkListItemList", BookmarkChecklistItem.class, QBookmarkChecklistItem.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commissionFee = createNumber("commissionFee", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> deposit = createNumber("deposit", Integer.class);

    public final EnumPath<com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseDirection> direction = createEnum("direction", com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseDirection.class);

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final com.mjutarzan.tarzan.domain.house.entity.QHouse house;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.mjutarzan.tarzan.domain.review.model.vo.LeaseType> leaseType = createEnum("leaseType", com.mjutarzan.tarzan.domain.review.model.vo.LeaseType.class);

    public final NumberPath<Integer> managementFee = createNumber("managementFee", Integer.class);

    public final NumberPath<Integer> parkingLotCoverage = createNumber("parkingLotCoverage", Integer.class);

    public final StringPath realEstate = createString("realEstate");

    public final StringPath realEstatePhoneNumber = createString("realEstatePhoneNumber");

    public final NumberPath<Integer> rent = createNumber("rent", Integer.class);

    public final NumberPath<Integer> roomCnt = createNumber("roomCnt", Integer.class);

    public final EnumPath<com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus> status = createEnum("status", com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.mjutarzan.tarzan.domain.user.entity.QUser user;

    public QBookmark(String variable) {
        this(Bookmark.class, forVariable(variable), INITS);
    }

    public QBookmark(Path<? extends Bookmark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmark(PathMetadata metadata, PathInits inits) {
        this(Bookmark.class, metadata, inits);
    }

    public QBookmark(Class<? extends Bookmark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.house = inits.isInitialized("house") ? new com.mjutarzan.tarzan.domain.house.entity.QHouse(forProperty("house")) : null;
        this.user = inits.isInitialized("user") ? new com.mjutarzan.tarzan.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

