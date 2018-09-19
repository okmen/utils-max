package com.utils_max;

import com.utils_max.enums.ResultStatusEnums;

public class ResultMsg {
	
	private ResultStatusEnums status;
	private String msg;
	private Object data;
	public ResultStatusEnums getStatus() {
		return status;
	}
	public void setStatus(ResultStatusEnums status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
