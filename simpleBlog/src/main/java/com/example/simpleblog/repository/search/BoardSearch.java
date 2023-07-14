package com.example.simpleblog.repository.search;

import com.example.simpleblog.domain.Board;
import com.example.simpleblog.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<BoardDTO> search(String[] types, String keyword, Pageable pageable);
}
