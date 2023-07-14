package com.example.simpleblog.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = 232698761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoard board;

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath replyText = createString("replyText");

    public final NumberPath<Long> rno = createNumber("rno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final StringPath writer = createString("writer");

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

