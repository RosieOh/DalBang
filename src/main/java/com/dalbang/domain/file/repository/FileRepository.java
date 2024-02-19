package com.dalbang.domain.file.repository;

import com.dalbang.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long > {

}
