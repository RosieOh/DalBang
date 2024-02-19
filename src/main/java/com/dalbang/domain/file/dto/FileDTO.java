package com.dalbang.domain.file.dto;

import com.dalbang.domain.file.entity.File;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDTO {
    private Long id;
    private String originFileName;
    private String fileName;
    private String filePath;

    public File toEntity() {
        File build = File.builder()
                .id(id)
                .originFileName(originFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public FileDTO(Long id, String originFileName, String fileName, String filePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
