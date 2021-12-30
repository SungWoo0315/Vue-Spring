package com.dream.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
/*
	BNO INT NOT NULL AUTO_INCREMENT,	-- 게시판 고유 번호 (자동증가)
	TITLE VARCHAR(100),	-- 게시글 제목
	CONTENT VARCHAR(1000),	-- 게시글 내용
	WRITER VARCHAR(100),	-- 글쓴이
	REGDATE TIMESTAMP DEFAULT NOW(),	-- 날짜(기본값 현재시각)
	PRIMARY KEY (BNO)	-- 게시판 번호 기본키 지정
*/
	int b_no;
	String subject;
	String writer;
	String content;
	String pwd;
	String email;
	String reg_date;
	String pic;
	
	int readcount;
	int group_no;
	int pring_no;
	int print_level;
	
//	Date reg_date;
}