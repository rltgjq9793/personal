package com.example.simpleblog;

import com.example.simpleblog.domain.Board;
import com.example.simpleblog.domain.BoardImage;
import com.example.simpleblog.dto.BoardDTO;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.repository.BoardRepository;
import com.example.simpleblog.service.BoardService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@SpringBootTest
@Log4j2
public class BoardTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testList(){
        boardService.getList(new PageRequestDTO()).getDtoList().stream().forEach(System.out::println);
    }

    @Test
    public void testRegister(){

        for(int i = 0; i< 223; i++){
            BoardDTO boardDTO = BoardDTO.builder()
                    .title("타이틀 테스트 "+i)
                    .content("내용 테스트 "+i)
                    .writer("작성"+i)
                    .password("123123").build();

            boardService.register(boardDTO);
        }
    }

    @Test
    public void readOne(){
        BoardDTO boardDTO = boardService.read(2L);
    }

    @Test
    public void testUpdate(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(1L)
                .title("제목 수정.......")
                .content("내용 수정........")
                .writer("작성자1")
                .password("123123").build();

        boardService.update(boardDTO);
    }

    @Test
    public void testDelete(){
        boardService.delete(2L);
    }

    @Test
    public void testInsertWithImages(){
/*        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.orElseThrow();*/

        Board board = Board.builder()
                .title("Image test")
                .content("첨부파일 테스트")
                .writer("작성자")
                .password("123123").build();

        for(int i = 0; i<3; i++){
            board.addImage(java.util.UUID.randomUUID().toString(), "file"+i+".jpg");
        }
        boardRepository.save(board);
    }

    @Test
    public void testReadWithImages(){

        //반드시 존재하는 bno로 확인
        Optional<Board> result = boardRepository.findByIdWithImages(100L);

        Board board = result.orElseThrow();
        for(BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        }
    }

}
