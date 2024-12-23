package com.store.comic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.MembersBean;
import com.store.comic.domain.StoresBean;
import com.store.comic.dto.BookDto;
import com.store.comic.dto.StoreDto;
import com.store.comic.response.ApiResponse;
import com.store.comic.service.BooksService;
import com.store.comic.service.MembersService;
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
@RequestMapping("/members")
@RestController
@Transactional
public class MembersController {
	@Autowired
	private MembersService membersService;
	
	//新增一個member
	@PostMapping("/insert")
	public ApiResponse<Object> insert(@RequestBody String json) {
		MembersBean membersBean = membersService.insert(json);
		if(membersBean!=null) {
			return ApiResponse.success(membersBean);	
		}
		return ApiResponse.fail(400, "error", "MembersController.insert false,Please check if member is exist");
	}
	
	//呼叫所有member
	@GetMapping("/getmembers")
	public ApiResponse<Object> getBook() {
		List<MembersBean> membersBeans = membersService.find();
		return ApiResponse.success(membersBeans);
	}
	
	//呼叫個別member
	@GetMapping("/findById/{id}")
	public ApiResponse<Object> findById(@PathVariable Integer id) {
		MembersBean membersBean = membersService.findById(id);
		if(membersBean!=null) {
			return ApiResponse.success(membersBean);
		}
		return ApiResponse.fail(400, "error", "MembersController.findById false,id can't be null");
	}
	
	//修改個別member
	@PutMapping("/update")
	public ApiResponse<Object> update(@RequestBody String json) {
		MembersBean membersBean = membersService.update(json);
		if(membersBean!=null) {
			return ApiResponse.success(membersBean);
		}
		return ApiResponse.fail(400,"error","MembersController.update false,id or book can't be null");
	}
	
	//刪除個別member
	@DeleteMapping("/delete/{id}")
	public ApiResponse<Object> delete(@PathVariable Integer id) {
		boolean condition = membersService.delete(id);
		if(condition==true) {
			return ApiResponse.success(condition);
		}
		return ApiResponse.fail(400, "error", "MembersController.delete false");
	}
	
	
}
