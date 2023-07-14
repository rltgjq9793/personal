package com.example.simpleblog;

import com.example.simpleblog.domain.Board;
import com.example.simpleblog.domain.Reply;
import com.example.simpleblog.dto.BoardDTO;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.ReplyDTO;
import com.example.simpleblog.repository.ReplyRepository;
import com.example.simpleblog.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){

        for(int i = 0; i< 100; i++){
            ReplyDTO replyDTO = ReplyDTO.builder()
                    .bno(227L)
                    .replyText("댓글 테스트 "+i)
                    .writer("작성"+i)
                    .password("123123").build();

            replyService.register(replyDTO);
        }
    }

    @Test
    public void readOne(){
        log.info(replyService.read(2L));
    }

    @Test
    public void testUpdate(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(2L)
                .replyText("댓글 수정 ").build();

        replyService.update(replyDTO);
    }

    @Test
    public void testDelete(){
        replyService.delete(2L);
    }

    @Test
    public void testDeleteAll(){
        replyRepository.deleteAllByBoardBno(220L);
    }
}
