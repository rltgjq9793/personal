package com.example.simpleblog.controller;

import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.PageResponseDTO;
import com.example.simpleblog.dto.ReplyDTO;
import com.example.simpleblog.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "Replies of Board", description = "GET 방식으로 특정 게시물의 댓글 목록 가져오기")
    @GetMapping(value="/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             @ModelAttribute(value = "requestDTO" ) PageRequestDTO pageRequestDTO){

        return replyService.getList(bno, pageRequestDTO);
    }

    @Operation(summary = "Read Reply", description = "GET 방식으로 특정 댓글 가져오기")
    @GetMapping(value="/{rno}")
    public ReplyDTO getOne(@PathVariable("rno") Long rno){

        return replyService.read(rno);
    }

    @Operation(summary = "Register Reply", description = "Post 방식으로 댓글 등록하기")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult){

        Map<String, Long> resultMap = new HashMap<>();
        log.info(replyDTO);
        if(bindingResult.hasErrors()){

            resultMap.put("error", 1L);
            return resultMap;   //에러가 발생할 시 error라는 이름의 json 객체를 전달
        }

        Long rno = replyService.register(replyDTO);
        resultMap.put("rno", rno);
        return resultMap;
    }

    @Operation(summary = "modify Reply", description = "PUT 방식으로 댓글 수정하기")
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> update(@RequestBody ReplyDTO replyDTO){

        replyService.update(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", replyDTO.getRno());
        return resultMap;
    }

    @Operation(summary = "Delete Replies", description = "DELETE 방식으로 댓글 삭제하기")
    @DeleteMapping("{rno}")
    public void remove(@PathVariable("rno") Long rno){
        replyService.delete(rno);
    }
}
