package com.mjutarzan.tarzan.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1471143218L;

    public static final QUser user = new QUser("user");

    public final ListPath<com.mjutarzan.tarzan.domain.board.entity.Board, com.mjutarzan.tarzan.domain.board.entity.QBoard> boardList = this.<com.mjutarzan.tarzan.domain.board.entity.Board, com.mjutarzan.tarzan.domain.board.entity.QBoard>createList("boardList", com.mjutarzan.tarzan.domain.board.entity.Board.class, com.mjutarzan.tarzan.domain.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark> bookmarkList = this.<com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark>createList("bookmarkList", com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark.class, com.mjutarzan.tarzan.domain.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final ListPath<com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem, com.mjutarzan.tarzan.domain.bookmark.entity.QChecklistItem> checkListItemList = this.<com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem, com.mjutarzan.tarzan.domain.bookmark.entity.QChecklistItem>createList("checkListItemList", com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem.class, com.mjutarzan.tarzan.domain.bookmark.entity.QChecklistItem.class, PathInits.DIRECT2);

    public final ListPath<com.mjutarzan.tarzan.domain.board.entity.Comment, com.mjutarzan.tarzan.domain.board.entity.QComment> commentList = this.<com.mjutarzan.tarzan.domain.board.entity.Comment, com.mjutarzan.tarzan.domain.board.entity.QComment>createList("commentList", com.mjutarzan.tarzan.domain.board.entity.Comment.class, com.mjutarzan.tarzan.domain.board.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final EnumPath<com.mjutarzan.tarzan.global.common.vo.SiGunGu> gu = createEnum("gu", com.mjutarzan.tarzan.global.common.vo.SiGunGu.class);

    public final BooleanPath haveAnimal = createBoolean("haveAnimal");

    public final BooleanPath haveCar = createBoolean("haveCar");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath jobAddress = createString("jobAddress");

    public final ComparablePath<org.locationtech.jts.geom.Point> jobLocation = createComparable("jobLocation", org.locationtech.jts.geom.Point.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final ListPath<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview> reviewList = this.<com.mjutarzan.tarzan.domain.review.entity.Review, com.mjutarzan.tarzan.domain.review.entity.QReview>createList("reviewList", com.mjutarzan.tarzan.domain.review.entity.Review.class, com.mjutarzan.tarzan.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final EnumPath<com.mjutarzan.tarzan.domain.user.model.vo.Role> role = createEnum("role", com.mjutarzan.tarzan.domain.user.model.vo.Role.class);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.mjutarzan.tarzan.domain.user.model.vo.SocialType> socialType = createEnum("socialType", com.mjutarzan.tarzan.domain.user.model.vo.SocialType.class);

    public final ListPath<com.mjutarzan.tarzan.domain.house.entity.UserHouse, com.mjutarzan.tarzan.domain.house.entity.QUserHouse> userHouseList = this.<com.mjutarzan.tarzan.domain.house.entity.UserHouse, com.mjutarzan.tarzan.domain.house.entity.QUserHouse>createList("userHouseList", com.mjutarzan.tarzan.domain.house.entity.UserHouse.class, com.mjutarzan.tarzan.domain.house.entity.QUserHouse.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

