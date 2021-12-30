package com.dream.vo;

import lombok.Data;

@Data
public class ResultVO {
	
	Object result;
	boolean success;
	String msg;
	
	// 생성자
	public ResultVO(boolean success, Object result, String msg) {
		this.result = result;
		this.success = false;
		this.msg = "";
	}
}