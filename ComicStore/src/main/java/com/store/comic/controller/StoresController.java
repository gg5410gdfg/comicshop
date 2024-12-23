package com.store.comic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.store.comic.domain.StoresBean;
import com.store.comic.dto.StoreDto;
import com.store.comic.response.ApiResponse;
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
@RequestMapping("/store")
@RestController
@Transactional
public class StoresController {
	@Autowired
	private StoresService storeService;
	
	//新增一個store
	@PostMapping("/insert")
	public ApiResponse<Object> insert(@RequestBody String json) {
		StoresBean storeBean = storeService.insert(json);
		if(storeBean!=null) {
			return ApiResponse.success(storeBean);	
		}
		return ApiResponse.fail(400, "error", "StoreController.insert false");
	}
	
	//呼叫所有store
	@GetMapping("/getstore")
	public ApiResponse<Object> getStore() {
		List<StoresBean> storeBeans = storeService.find();
		return ApiResponse.success(storeBeans);
	}
	
	//呼叫個別store
	@GetMapping("/findById/{id}")
	public ApiResponse<Object> findById(@PathVariable Integer id) {
		StoresBean storeBean = storeService.findById(id);
		if(storeBean!=null) {
			return ApiResponse.success(storeBean);
		}
		return ApiResponse.fail(400, "error", "StoreController.findById false,id can't be null");
	}
	
	//修改個別商店
	@PutMapping("/update")
	public ApiResponse<Object> update(@RequestBody String json) {
		StoresBean storeBean = storeService.update(json);
		if(storeBean!=null) {
			return ApiResponse.success(storeBean);
		}
		return ApiResponse.fail(400,"error","StoreController.update false,id or store can't be null");
	}
	
	//刪除個別商店
	@DeleteMapping("/delete/{id}")
	public ApiResponse<Object> delete(@PathVariable Integer id) {
		System.out.println("id");
		System.out.println(id);
		boolean condition = storeService.delete(id);
		if(condition==true) {
			return ApiResponse.success(condition);
		}
		return ApiResponse.fail(400, "error", "StoreController.delete false");
	}
	
	
}
