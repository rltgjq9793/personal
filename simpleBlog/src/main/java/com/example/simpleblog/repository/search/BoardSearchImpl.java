package com.example.simpleblog.repository.search;

import com.example.simpleblog.domain.Board;
import com.example.simpleblog.domain.QBoard;
import com.example.simpleblog.domain.QReply;
import com.example.simpleblog.dto.BoardDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(ModelMapper modelMapper) {
        super(Board.class);
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;


    public BoardDTO entityToDto(Board board){
        /*BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .password(board.getPassword()).build();*/

        return modelMapper.map(board, BoardDTO.class);
    }


    @Override
    public Page<BoardDTO> search(String[] types, String keyword, Pageable pageable) {

        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply; //새로 추가된 부분

        JPQLQuery<Board> query = from(qBoard);
        query.leftJoin(qReply).on(qReply.board.eq(qBoard)); //새로 추가된 부분

        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){
                switch(type){
                    case "t":
                        booleanBuilder.or(qBoard.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(qBoard.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(qBoard.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

       query.where(qBoard.bno.gt(0)); // bno가 0보다 크다는 조건
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(qBoard, qReply.countDistinct())
                .groupBy(qBoard);

        long count = query.fetch().size();

        getQuerydsl().applyPagination(pageable, query);


        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<BoardDTO> list = tupleList.stream().map(board -> {
                Board board1 = (Board) board.get(qBoard);
                long replyCount = board.get(1, Long.class);
                BoardDTO dto = entityToDto(board1);
                dto.setReplyCount(replyCount);

                return dto;
                }).collect(Collectors.toList());

        //long count = query.fetchCount(); fetchCount() 함수는 현재 권장되지 않는다. fetch().size()로 대체하는 것이 좋다

        return new PageImpl<>(list, pageable, count);
    }
}
