package com.example.simpleblog.service;

import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.PageResponseDTO;
import com.example.simpleblog.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    PageResponseDTO<ReplyDTO> getList(Long bno, PageRequestDTO pageRequestDTO);

    ReplyDTO read(Long rno);

    void update(ReplyDTO replyDTO);

    void delete(Long rno);

    void deleteAll(Long bno);
}
