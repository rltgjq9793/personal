package com.example.simpleblog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 5, nullable = false)
    private String writer;

    @Column(length = 10, nullable = false)
    private String password;

    public void change(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @OneToMany(mappedBy = "board",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @BatchSize(size=20)
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size()).build();

        imageSet.add(boardImage);
    }

    public void clearImages(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));

        this.imageSet.clear();
    }
}
