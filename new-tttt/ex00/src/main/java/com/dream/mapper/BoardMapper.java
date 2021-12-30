package com.dream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dream.vo.BoardVO;

@Mapper
public interface BoardMapper {

	public List<BoardVO> getBoardList();
	
	
	public int regBoard(BoardVO vo);
	
	
	public BoardVO getBoardDetail(int b_no);


	public int editBoard(BoardVO vo);


	public int deleteBoard(BoardVO vo);
	
}