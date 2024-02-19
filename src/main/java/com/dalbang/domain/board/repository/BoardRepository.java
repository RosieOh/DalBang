package com.dalbang.domain.board.repository;

import com.dalbang.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.bno = :bno")
    Optional<Board> findById(@Param("bno") Long bno);

    @Query("SELECT b from Board b where b.boardType = :boardType")
    List<Board> findByBoardType(@Param("boardType") String boardType);

    Page<Board> findByBoardType(String boardType, Pageable pageable);
}
