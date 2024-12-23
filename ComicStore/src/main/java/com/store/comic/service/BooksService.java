package com.store.comic.service;



import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.StoresBean;
import com.store.comic.dto.BookDto;
import com.store.comic.dto.PhotoTypeDto;
import com.store.comic.repository.BooksRepository;
import com.store.comic.repository.StoreRepository;
import com.store.comic.utils.PhotoTurn;


@Service
public class BooksService {
	@Autowired
	private BooksRepository bookRepository;
	@Autowired
	private StoreRepository storeRepository;
	public BooksBean insert(String string) {
		BooksBean booksBean=new BooksBean();
		try {
			
			JSONObject json=new JSONObject(string);
			
			String name=json.isNull("name")? null:json.getString("name");
			Integer totalQuantity=json.isNull("totalQuantity")? null:json.getInt("totalQuantity");
			Integer nowQuantity=json.isNull("nowQuantity")? null:json.getInt("nowQuantity");
			Timestamp timestamp = Timestamp.from(Instant.now());
			String photo=json.isNull("photo")? null:json.getString("photo");		
			String introduce=json.isNull("introduce")? null:json.getString("introduce");
			String author=json.isNull("author")? null:json.getString("author");
			String publicationDateStr=json.isNull("publicationDate")? null:json.getString("publicationDate");
			System.out.println("111");
			Integer storeId=json.isNull("storeId")? null:json.getInt("storeId");
			Integer price=json.isNull("price")? null:json.getInt("price");
			Boolean checkCondition=json.isNull("checkCondition")? null:json.getBoolean("checkCondition");
			
			// 日期轉換
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate localDate = LocalDate.parse(publicationDateStr, formatter);
	        Date publicationDate = java.sql.Date.valueOf(localDate);
	        //如果有這家店才新增
	        if(storeId!=null) {
	        	Optional<StoresBean> optional = storeRepository.findById(storeId);
	        	if(optional.isPresent()) {
	        		StoresBean storesBean = optional.get();
	        		booksBean.setName(name);
	        		booksBean.setTotalQuantity(totalQuantity);
	        		booksBean.setNowQuantity(nowQuantity);
	        		booksBean.setCreateTime(timestamp);
	        		booksBean.setUpdateTime(timestamp);
	        		//靜態工具類轉換base64成含mimeType(String)和byte[]的dto
	        		if(photo!=null) {
	        			PhotoTypeDto byteDto = PhotoTurn.base64ToByte(photo);
	        			booksBean.setMimeType(byteDto.getMimeType());
	        			booksBean.setPhoto(byteDto.getPhoto());
	        		}
	        		booksBean.setIntroduce(introduce);
	        		booksBean.setAuthor(author);
	        		booksBean.setPublicationDate(publicationDate);
	        		booksBean.setPrice(price);
	        		booksBean.setStore(storesBean);
	        		booksBean.setCheckCondition(checkCondition);
	        		return bookRepository.save(booksBean);
	        	}
	        }
		} catch (JSONException e) {
			System.out.println("BooksService.insert:JSONException error");
		}
		return null;
	}
	
	public BooksBean update(String string) {
		try {
			JSONObject json = new JSONObject(string);
			Integer id = json.isNull("id") ? null : json.getInt("id");
			String name=json.isNull("name")? null:json.getString("name");
			Integer totalQuantity=json.isNull("totalQuantity")? null:json.getInt("totalQuantity");
			Integer nowQuantity=json.isNull("nowQuantity")? null:json.getInt("nowQuantity");
			Timestamp timestamp = Timestamp.from(Instant.now());
			String photo=json.isNull("photo")? null:json.getString("photo");
			String introduce=json.isNull("introduce")? null:json.getString("introduce");
			String author=json.isNull("author")? null:json.getString("author");
			Integer price=json.isNull("price")? null:json.getInt("price");
			String publicationDateStr=json.isNull("publicationDate")? null:json.getString("publicationDate");
			Boolean checkCondition=json.isNull("checkCondition")? null:json.getBoolean("checkCondition");
			// 日期轉換
	        LocalDate localDate = LocalDate.parse(publicationDateStr);
	        Date publicationDate = Date.valueOf(localDate);

			if(id!=null) {
				Optional<BooksBean> optional = bookRepository.findById(id);
				if(optional.isPresent()) {
					BooksBean booksBean = optional.get();
					booksBean.setName(name);
			        booksBean.setTotalQuantity(totalQuantity);
			        booksBean.setNowQuantity(nowQuantity);
			        booksBean.setUpdateTime(timestamp);
			        booksBean.setPrice(price);
					//靜態工具類轉換base64成含mimeType(String)和byte[]的dto
					if(photo!=null) {
						PhotoTypeDto byteDto = PhotoTurn.base64ToByte(photo);
						booksBean.setMimeType(byteDto.getMimeType());
						booksBean.setPhoto(byteDto.getPhoto());
					}
					booksBean.setIntroduce(introduce);
					booksBean.setAuthor(author);
					booksBean.setPublicationDate(publicationDate);
					booksBean.setCheckCondition(checkCondition);
					return bookRepository.save(booksBean);
				}
			}
		} catch (JSONException e) {
			System.out.println("BookService.update:update error");
		}
		return null;
	}
	
	//修改訂單成立後書籍庫存
	public BooksBean updateByOrder(Integer bookId,Integer quantity,String condition) {
		Timestamp timestamp = Timestamp.from(Instant.now());
		if(bookId!=null) {
			Optional<BooksBean> optional = bookRepository.findById(bookId);
			if(optional.isPresent()) {
				BooksBean booksBean = optional.get();
				if(condition.equals("rent")) {
					if(booksBean.getNowQuantity()>=quantity) {
						booksBean.setNowQuantity(booksBean.getNowQuantity()-quantity);
					}
				}else if(condition.equals("back")) {
					booksBean.setNowQuantity(booksBean.getNowQuantity()+quantity);
				}
				booksBean.setUpdateTime(timestamp);
				return bookRepository.save(booksBean);
			
			}
		}
		return null;
	}
	
	public boolean delete(Integer id) {
		if(id!=null && bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public BookDto findById(Integer id) {
		if(id!=null) {
			Optional<BooksBean> optional = bookRepository.findById(id);
			if(optional.isPresent()) {
				BooksBean booksBean = optional.get();
				String photo = PhotoTurn.byteToBase64(booksBean);
				BookDto dto=new BookDto();
				dto.setId(booksBean.getId());
				dto.setName(booksBean.getName());
				dto.setTotalQuantity(booksBean.getTotalQuantity());
				dto.setNowQuantity(booksBean.getNowQuantity());
				dto.setPhoto(photo);
				dto.setIntroduce(booksBean.getIntroduce());
				dto.setAuthor(booksBean.getAuthor());
				dto.setPublicationDate(booksBean.getPublicationDate());
				dto.setPrice(booksBean.getPrice());
				dto.setCheckCondition(booksBean.getCheckCondition());
				dto.setStoreId(booksBean.getStore().getId());
				return dto;
			}
		}
		return null;
	}
	
	public List<BookDto> find(Integer storeId) {
		List<BooksBean> booksBeans = bookRepository.findBooksByStoreId(storeId);
		List<BookDto> bookDtos=new ArrayList<>();
		for (BooksBean booksBean : booksBeans) {
			String photo = PhotoTurn.byteToBase64(booksBean);
			BookDto dto=new BookDto();
			dto.setId(booksBean.getId());
			dto.setName(booksBean.getName());
			dto.setTotalQuantity(booksBean.getTotalQuantity());
			dto.setNowQuantity(booksBean.getNowQuantity());
			dto.setPhoto(photo);
			dto.setIntroduce(booksBean.getIntroduce());
			dto.setAuthor(booksBean.getAuthor());
			dto.setPublicationDate(booksBean.getPublicationDate());
			dto.setPrice(booksBean.getPrice());
			dto.setCheckCondition(booksBean.getCheckCondition());
			dto.setStoreId(storeId);
			bookDtos.add(dto);
		}
		return bookDtos;
	}
}
