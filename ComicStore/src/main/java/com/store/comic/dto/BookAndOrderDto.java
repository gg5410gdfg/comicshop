package com.store.comic.dto;



import java.sql.Timestamp;


import com.store.comic.domain.BooksBean;
import com.store.comic.domain.MemberOrderDetailsBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookAndOrderDto {

	private Integer id;
	private Integer memberId;
	private Timestamp tradeTime;
	private Integer checkReturn;
	private BooksBean booksBean;
	private MemberOrderDetailsBean memberOrderDetailsBean;

}
