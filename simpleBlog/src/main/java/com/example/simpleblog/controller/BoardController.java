package com.example.simpleblog.controller;

import com.example.simpleblog.dto.BoardDTO;
import com.example.simpleblog.dto.PageRequestDTO;
import com.example.simpleblog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(Model model,
                     @ModelAttribute(value = "requestDTO" ) PageRequestDTO pageRequestDTO){
        model.addAttribute("responseDTO",boardService.getList(pageRequestDTO));
    }


    @GetMapping({"/read", "/modify"})
    public void readModifyGET(Model model, Long bno,
                              @ModelAttribute(value = "requestDTO" )PageRequestDTO pageRequestDTO){
        BoardDTO dto = boardService.read(bno);

        model.addAttribute("dto", dto);
    }

    @GetMapping("/register")
    public void registerGet(){}

    @PostMapping("/register")
    public String registerPost(RedirectAttributes redirectAttributes, @Valid BoardDTO boardDTO,
                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info(boardDTO.getFileName());
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addAttribute("bno", bno);

        return "redirect:/board/read";
    }

    @PostMapping("/modify")
    public String modifyPOST(RedirectAttributes redirectAttributes,
                             @Valid BoardDTO boardDTO, BindingResult bindingResult,
                             @ModelAttribute(value = "requestDTO" )PageRequestDTO pageRequestDTO){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/list";
        }

        boardService.update(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read?"+pageRequestDTO.getLink();
    }

    @PostMapping("/delete")
    public String deletePOST(Long bno){
        boardService.delete(bno);

        return "redirect:/board/list";
    }


}
