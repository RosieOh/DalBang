package com.dalbang.domain.board.repository;

import com.dalbang.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
    /*
    QuerydslPredicateExecutor<Entity>
    -> Dynamic QueryDSL을 활용할 때 사용
     */

    @Query("select b from Board b where b.id = :id")
    Optional<Board> findById(@Param("id") Long id);

    @Query("select b from Board b where b.boardType = :boardType")
    List<Board> findByBoardType(@Param("boardType") String boardType);

    @Query("select b from Board b where b.writer = :writer")
    Optional<Board> findByName(@Param("writer") String writer);

    Page<Board> findByBoardType(Pageable pageable, String boardType);

    // 메인 페이지 최신 공지사항 5개 불러오기
    @Query("select b from Board b where b.id = 0 order by b.pinned desc, b.createdTime desc LIMIT 5")
    List<Board> newNoticeList();

    // 고정 게시글 상위 조회 + 최신 작성순
    @Query("select b from Board b order by b.pinned desc, b.createdTime desc")
    List<Board> findAll();


}
