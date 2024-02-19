package com.dalbang.domain.board.service;

import com.dalbang.domain.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    public BoardDTO findByBno(Long bno);

    public List<BoardDTO> findAll(BoardDTO boardDTO);

    public void register(BoardDTO boardDTO);

    public void modify(BoardDTO boardDTO);

    public void remove(Long bno);

    public List<BoardDTO>findByBoardType(String boardType);
}
