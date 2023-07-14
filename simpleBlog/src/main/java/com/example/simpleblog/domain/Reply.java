package com.example.simpleblog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Table(name="Reply", indexes = {@Index(name="idx_reply_board_bno", columnList = "board_bno")})
public class Reply extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(length = 6, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String replyText;

    @Column(length = 8, nullable = false)
    private String password;

    public void changeText(String text){
        this.replyText = text;
    }
}
