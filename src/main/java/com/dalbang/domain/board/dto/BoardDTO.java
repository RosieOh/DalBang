package com.dalbang.domain.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    @NotEmpty
    @Size(max = 200)
    private String title;

    @NotEmpty
    @Size(max = 2000)
    private String content;

    @NotEmpty
    @Size(max = 50)
    private String boardType;

    @NotEmpty
    @Size(max = 50)
    private String writer;

    private List<Long> fileIds;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private boolean pinned;

    private boolean privated;

    private boolean deleteType;
}
