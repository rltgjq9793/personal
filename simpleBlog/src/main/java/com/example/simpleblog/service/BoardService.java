package com.example.simpleblog.service;

import com.example.simpleblog.dto.BoardDTO;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {

    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(BoardDTO board);

    BoardDTO read(Long bno);

    void delete(Long bno);

    void update(BoardDTO board);
}
