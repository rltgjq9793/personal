package com.example.simpleblog.service;

import com.example.simpleblog.domain.Board;
import com.example.simpleblog.dto.BoardDTO;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.PageResponseDTO;
import com.example.simpleblog.repository.BoardRepository;
import com.example.simpleblog.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final ReplyRepository replyRepository;

    public BoardDTO entityToDto(Board board){
       BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .password(board.getPassword()).build();

       List<String> fileName =
       board.getImageSet().stream().sorted().map(boardImage ->
           boardImage.getUuid()+"_"+boardImage.getFileName()
       ).collect(Collectors.toList());

        boardDTO.setFileName(fileName);

//        return modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    public Board dtoToEntity(BoardDTO boardDTO){
       Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .password(boardDTO.getPassword()).build();

       if(boardDTO.getFileName() != null){
           boardDTO.getFileName().forEach(fileName -> {
               String[] arr = fileName.split("_");
               board.addImage(arr[0], arr[1]);
           });
       }
        /*return modelMapper.map(boardDTO, Board.class);*/

        return board;
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable();

        Page<BoardDTO> result = boardRepository.search(types, keyword, pageable);

        return PageResponseDTO.<BoardDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO read(Long bno) {
        Optional<Board> result = boardRepository.findByIdWithImages(bno);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = entityToDto(board);

        return boardDTO;
    }

    @Override
    public void delete(Long bno) {

        replyRepository.deleteAllByBoardBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void update(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getWriter());

        board.clearImages();

        if(boardDTO.getFileName() != null){
            for(String fileName : boardDTO.getFileName()){
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            }
        }

        boardRepository.save(board);
    }
}
