package com.example.simpleblog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    @NotEmpty(message = "제목을 입력해주세요")
    @Size(min = 3, max = 15, message = "제목은 최소 3글자 이상, 15글자 이하여야 합니다")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    @Size(max = 2000, message = "내용이 제한된 글자수를 초과했습니다")
    private String content;

    @NotEmpty(message = "작성자를 입력해주세요")
    @Size(max = 5, message = "작성자는 5글자를 넘을 수 없습니다")
    private String writer;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 3, max = 6, message = "비밀번호는 최소 3자리 이상, 6자리 이하여야 합니다")
    private String password;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    private Long replyCount;

    private List<String> fileName;
}
