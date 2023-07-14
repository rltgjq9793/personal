package com.example.simpleblog.repository;

import com.example.simpleblog.domain.Reply;
import com.example.simpleblog.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findAllByBoardBno(Long bno, Pageable pageable);

    void deleteAllByBoardBno(Long bno);

}
