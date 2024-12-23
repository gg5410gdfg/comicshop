package com.store.comic.dto;

import java.security.Timestamp;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

	private Integer id;
	private String name;
	private Integer totalQuantity;
	private Integer nowQuantity;
	private String photo;
	private String introduce;
	private String author;
	private Date publicationDate;
	private Integer price;
	private boolean checkCondition;
	private Integer storeId;

}
