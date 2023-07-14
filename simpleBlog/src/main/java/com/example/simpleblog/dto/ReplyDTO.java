package com.example.simpleblog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class ReplyDTO {

    private Long rno;

    @NotNull
    private Long bno;

    @NotEmpty(message = "댓글을 입력해주세요")
    @Size(max = 100, message = "댓글은 100자를 초과할 수 없습니다")
    private String replyText;

    @NotEmpty(message = "작성자 명을 입력해주세요")
    @Size(max = 5, message = "작성자 명은 5자를 초과할 수 없습니다")
    private String writer;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(max = 8, message = "비밀번호는 8자리를 초과할 수 없습니다")
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore
    private LocalDateTime updateDate;
}
