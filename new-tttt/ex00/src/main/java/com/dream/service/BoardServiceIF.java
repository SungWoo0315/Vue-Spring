package com.dream.service;

import java.util.List;

import com.dream.vo.BoardVO;

public interface BoardServiceIF {
	
	public List<BoardVO> getBoardList();
	
	
	public Boolean regBoard(BoardVO vo);
	
	
	public BoardVO getBoardDetail(BoardVO vo);
	
	
	public Object editBoard(BoardVO vo);

}