package com.store.comic.dto;

import java.security.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class MemberOrderDto {

	private Integer id;
	private Timestamp tradeTime;
	private List<MemberOrderDtail> memberOrderDtails;
	
	public class MemberOrderDtail{
		private Integer id;
		private String bookName;
		private Integer quantity;
	}
}
