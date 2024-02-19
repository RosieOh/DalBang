package com.dalbang.domain.board.service;

import com.dalbang.domain.board.dto.BoardDTO;
import com.dalbang.domain.board.entity.Board;
import com.dalbang.domain.board.repository.BoardRepository;
import com.dalbang.global.constant.BoardType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    // 특정 게시판의 역할에 따라 타입별로 생성
    // 추가로 생성 시 따로 코드 추가 진행 필요
    public void createAndSaveBoards() {
        List<Board> board = new ArrayList<>();

        Board noticeBoard = new Board();
        noticeBoard.setTitle("공지사항");
        noticeBoard.setContent("내용");
        noticeBoard.setBoardType(BoardType.NOTICE.toString());
        board.add(noticeBoard);

        Board qnaBoard = new Board();
        qnaBoard.setTitle("질문 및 답변");
        qnaBoard.setContent("답변");
        qnaBoard.setBoardType(BoardType.Qna.toString());
        board.add(qnaBoard);

        Board test1Board = new Board();
        test1Board.setTitle("테스트 게시판");
        test1Board.setContent("내용");
        test1Board.setBoardType(BoardType.TEST1.toString());
        board.add(test1Board);

        Board test2Board = new Board();
        test2Board.setTitle("테스트2 게시판");
        test2Board.setContent("내용");
        test2Board.setBoardType(BoardType.TEST2.toString());
        board.add(test2Board);

        // 생성된 게시판들 저장
        boardRepository.saveAll(board);
    }


    // boardType에 따라 자동으로 게시판이 분리되는 역할
    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> boardList = boardRepository.findByBoardType(boardType);

        if (boardList != null && !boardList.isEmpty()) {
            List<BoardDTO> boardDTOList = boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public BoardDTO findByBno(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        BoardDTO boardDTO =modelMapper.map(result, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public List<BoardDTO> findAll(BoardDTO boardDTO) {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board1 : boardList) {
            Board board = Board.builder()
                    .bno(boardDTO.getBno())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .boardType(boardDTO.getBoardType())
                    .writer(boardDTO.getWriter())
                    .fileId(boardDTO.getFileId())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Override
    public void register(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .boardType(boardDTO.getBoardType())
                .fileId(boardDTO.getFileId())
                .build();
        boardRepository.save(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }
}
