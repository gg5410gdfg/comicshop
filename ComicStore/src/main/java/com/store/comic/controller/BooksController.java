package com.store.comic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.StoresBean;
import com.store.comic.dto.BookDto;
import com.store.comic.dto.StoreDto;
import com.store.comic.response.ApiResponse;
import com.store.comic.service.BooksService;
import com.store.comic.service.StoresService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin
@RequestMapping("/books")
@RestController
@Transactional
public class BooksController {
	@Autowired
	private BooksService booksService;
	
	//新增一個book
	@PostMapping("/insert")
	public ApiResponse<Object> insert(@RequestBody String json) {
		BooksBean booksBean = booksService.insert(json);
		if(booksBean!=null) {
			return ApiResponse.success(booksBean);	
		}
		return ApiResponse.fail(400, "error", "BooksController.insert false,Please check if store is exist");
	}
	
	//呼叫所有book
	@GetMapping("/getbook/{storeId}")
	public ApiResponse<Object> getBook(@PathVariable Integer storeId) {
		List<BookDto> booksBeans = booksService.find(storeId);
		return ApiResponse.success(booksBeans);
	}
	
	//呼叫個別book
	@GetMapping("/findById/{id}")
	public ApiResponse<Object> findById(@PathVariable Integer id) {
		BookDto dto = booksService.findById(id);
		if(dto!=null) {
			return ApiResponse.success(dto);
		}
		return ApiResponse.fail(400, "error", "BooksController.findById false,id can't be null");
	}
	
	//修改個別book
	@PutMapping("/update")
	public ApiResponse<Object> update(@RequestBody String json) {
		BooksBean booksBean = booksService.update(json);
		if(booksBean!=null) {
			return ApiResponse.success(booksBean);
		}
		return ApiResponse.fail(400,"error","BooksController.update false,id or book can't be null");
	}
	
	//刪除個別book
	@DeleteMapping("/delete/{id}")
	public ApiResponse<Object> delete(@PathVariable Integer id) {
		boolean condition = booksService.delete(id);
		if(condition==true) {
			return ApiResponse.success(condition);
		}
		return ApiResponse.fail(400, "error", "BooksController.delete false");
	}
	
	
}
