package com.store.comic.dto;

import java.security.Timestamp;


import lombok.Data;

@Data
public class StoreDto {

	private Integer id;
	private String name;
	private String telePhone;
	private Timestamp createTimestamp;
	private Timestamp updateTime;
	private String address;
}
