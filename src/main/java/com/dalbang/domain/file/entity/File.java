package com.dalbang.domain.file.entity;

import com.dalbang.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public File(Long id, String originFileName, String filePath, String fileName) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.board = board;
    }

    public File(String originFileName, String fileName, String filePath, Board board) {
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.board = board;
    }

    public void change(Long id, String fileName, String filePath, String originFileName) {
        this.id = id;
        this.filePath = filePath;
        this.originFileName = originFileName;
        this.fileName = fileName;
    }
}
