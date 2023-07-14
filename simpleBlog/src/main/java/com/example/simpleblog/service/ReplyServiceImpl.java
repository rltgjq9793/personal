package com.example.simpleblog.service;

import com.example.simpleblog.domain.Reply;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.dto.PageResponseDTO;
import com.example.simpleblog.dto.ReplyDTO;
import com.example.simpleblog.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    public ReplyDTO entityToDto(Reply reply){
        return modelMapper.map(reply, ReplyDTO.class);
    }

    public Reply dtoToEntity(ReplyDTO replyDTO){
        return modelMapper.map(replyDTO, Reply.class);
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        Long rno = replyRepository.save(dtoToEntity(replyDTO)).getRno();
        return rno;
    }

    @Override
    public PageResponseDTO<ReplyDTO> getList(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.findAllByBoardBno(bno, pageable);

        List<ReplyDTO> dtoList = result
                .stream().map(reply -> entityToDto(reply))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements()).build();
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.orElseThrow();

        ReplyDTO replyDTO = entityToDto(reply);

        return replyDTO;
    }

    @Override
    public void update(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
        Reply reply = result.orElseThrow();

        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public void deleteAll(Long bno){
        replyRepository.deleteAllByBoardBno(bno);
    }
}
