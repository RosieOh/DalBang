package com.dalbang.domain.board.service;

import com.dalbang.domain.board.dto.BoardDTO;
import com.dalbang.domain.board.entity.Board;
import com.dalbang.domain.board.entity.QBoard;
import com.dalbang.domain.board.repository.BoardRepository;
import com.dalbang.domain.file.entity.File;
import com.dalbang.global.constant.BoardType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory jpaQueryFactory;

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


    @Override
    public BoardDTO findById(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        BoardDTO boardDTO = modelMapper.map(result, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        log.info(boardDTO.getBoardType());
        Board board = Board.builder()
                .id(boardDTO.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .boardType(boardDTO.getBoardType())
                .writer(boardDTO.getWriter())
                .pinned(boardDTO.isPinned())
                .privated(boardDTO.isPrivated())
                .build();
        boardRepository.save(board);
        return board.getId();
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.isPinned(), boardDTO.isPrivated());
        boardRepository.save(board);
    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO getBoard(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    // boardType에 따라 자동으로 게시판이 분리되는 역할
    @Override
    public List<BoardDTO> findByBoardType(String boardType) {
        List<Board> boardList = boardRepository.findByBoardType(boardType);

        if (boardList != null && !boardList.isEmpty()) {
            List<BoardDTO> boardDTOList = boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                    .collect(Collectors.toList());
            return boardDTOList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<BoardDTO> newNoticeList() {
        List<Board> noticesList = boardRepository.newNoticeList();

        // entity 내용 출력
        for (Board notice : noticesList) {
            log.info("Notice 게시물 출력 : " + notice.getTitle());
        }
        List<BoardDTO> newNoticeList = noticesList.stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return newNoticeList;
    }

    // 고정 게시물 추가 기능
    @Override
    public int countPinned(List<BoardDTO> boardDTOList) {
        int pinnedCount = 0;
        for (BoardDTO boardDTO : boardDTOList) {
            if (boardDTO.isPinned())
                pinnedCount++;
        }
        return pinnedCount;
    }

    @Override
    public Page<Board> searchBoard(String keyword, Integer id, Pageable pageable) {
        BooleanBuilder where = new BooleanBuilder();

        // 키워드가 있는 경우 이름 필터링
        if (StringUtils.hasText(keyword)) {
            where.and(QBoard.board.title.containsIgnoreCase(keyword));
        }


        // boardType이 Class_Notice인 것만 필터링
        where.and(QBoard.board.boardType.eq(String.valueOf(BoardType.NOTICE)));
        // deleteType이 false인 애들만 필터링 (삭제되지 않은 게시물)
        where.and(QBoard.board.deleteType.eq(false));

        // 페이징 처리
        JPAQuery<Board> query = jpaQueryFactory
                .selectFrom(QBoard.board)
                .where(where)
                .orderBy(QBoard.board.pinned.desc(), QBoard.board.createdTime.desc());

        // 페이징 처리된 결과 반환
        QueryResults<Board> results = query
                .offset(pageable.getOffset()) // 오프셋 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetchResults(); // 결과 가져오기

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public int countPinnedPaging(Page<Board> pagingPinList) {
        int PinnedPaging = 0;
        for (Board board : pagingPinList) {
            if (board.isPinned())
                PinnedPaging++;
        }
        return PinnedPaging;
    }

    // 다중 업로드
    @Override
    public Board uploadFile(Long boardId, File file) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            file.setBoard(board);
            board.getFiles().add(file);
            return boardRepository.save(board);
        } else {
            throw new IllegalArgumentException("Board not found with id: " + boardId);
        }
    }
}
