package com.dalbang.domain.board.service;

import com.dalbang.domain.board.dto.BoardDTO;
import com.dalbang.domain.board.entity.Board;
import com.dalbang.domain.file.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    public BoardDTO findById(Long id);

    public List<Board> boardList();

    public Long register(BoardDTO boardDTO);

    public void modify(BoardDTO boardDTO);

    public void remove(Long id);

    public BoardDTO getBoard(Long id);

    public List<BoardDTO> findByBoardType(String boardType);

    // 메인페이지 최신 공지사항 5개 불러오기
    public List<BoardDTO> newNoticeList();

    public int countPinned(List<BoardDTO> boardDTOList);

    // 검색 & 페이징
    public Page<Board> searchBoard(String keyword, Integer id, Pageable pageable);
    public int countPinnedPaging(Page<Board> pagingPinList);
    public Board uploadFile(Long boardId, File file);
}