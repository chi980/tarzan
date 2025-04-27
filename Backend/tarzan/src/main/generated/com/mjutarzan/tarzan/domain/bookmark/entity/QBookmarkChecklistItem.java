package com.mjutarzan.tarzan.domain.bookmark.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmarkChecklistItem is a Querydsl query type for BookmarkChecklistItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmarkChecklistItem extends EntityPathBase<BookmarkChecklistItem> {

    private static final long serialVersionUID = -1598323791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmarkChecklistItem bookmarkChecklistItem = new QBookmarkChecklistItem("bookmarkChecklistItem");

    public final QBookmark bookmark;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType> type = createEnum("type", com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType.class);

    public final BooleanPath value = createBoolean("value");

    public QBookmarkChecklistItem(String variable) {
        this(BookmarkChecklistItem.class, forVariable(variable), INITS);
    }

    public QBookmarkChecklistItem(Path<? extends BookmarkChecklistItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmarkChecklistItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmarkChecklistItem(PathMetadata metadata, PathInits inits) {
        this(BookmarkChecklistItem.class, metadata, inits);
    }

    public QBookmarkChecklistItem(Class<? extends BookmarkChecklistItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookmark = inits.isInitialized("bookmark") ? new QBookmark(forProperty("bookmark"), inits.get("bookmark")) : null;
    }

}

