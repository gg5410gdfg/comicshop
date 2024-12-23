package com.store.comic.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.MemberOrderDetailsBean;
import com.store.comic.domain.MemberOrdersBean;
import com.store.comic.dto.BookAndOrderDto;
import com.store.comic.response.ApiResponse;
import com.store.comic.service.BooksService;
import com.store.comic.service.MemberOrderDetailsService;
import com.store.comic.service.MemberOrdersService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RequestMapping("/order")
@RestController
@Transactional
public class MemberOrdersController {
	@Autowired
	private MemberOrdersService memberOrdersService;
	@Autowired
	private MemberOrderDetailsService memberOrderDetailsService;
	@Autowired
	private BooksService booksService;
	
	//新增一個order
	@PostMapping("/insert")
	public ApiResponse<Object> insert(@RequestBody String string) {
		try {
			JSONObject json=new JSONObject(string);
			Integer quantity=json.isNull("quantity")? null:json.getInt("quantity");
			Integer bookId=json.isNull("bookId")? null:json.getInt("bookId");
			Integer memberId=json.isNull("memberId")? null:json.getInt("memberId");
			//先執行庫存修改，如果數量不符直接return error
			if(bookId!=null && quantity!= null) {
				if(booksService.updateByOrder(bookId,quantity,"rent")==null) {
					return ApiResponse.fail(400, "error", "MemberOrdersController.insert false");
				}
				MemberOrdersBean storeBean = memberOrdersService.insert(bookId,memberId);
				if(storeBean!=null) {
					memberOrderDetailsService.insert(bookId,quantity,storeBean.getId());	
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ApiResponse.success("success");
	}
	
	//呼叫所有該會員order
	@GetMapping("/getorder/{memberId}")
	public ApiResponse<Object> getOrder(@PathVariable Integer memberId) {
		List<BookAndOrderDto> bookAndOrderDtos = memberOrdersService.find(memberId);
		if(bookAndOrderDtos==null) {
			return ApiResponse.fail(400,"error","MemberOrdersController.getOrder false,memberId can't be null");
		}
		return ApiResponse.success(bookAndOrderDtos);
	}
	
	//呼叫所有會員order
		@GetMapping("/getorder/all")
		public ApiResponse<Object> getOrder() {
			List<BookAndOrderDto> bookAndOrderDtos = memberOrdersService.findAll();
			if(bookAndOrderDtos==null) {
				return ApiResponse.fail(400,"error","MemberOrdersController.getOrder false,memberId can't be null");
			}
			return ApiResponse.success(bookAndOrderDtos);
		}

	//修改個別order(客戶已點擊還書,店家未確認)
	@PutMapping("/update/member/{orderID}")
	public ApiResponse<Object> updateMemberReturn(@PathVariable Integer orderID) {
		MemberOrdersBean memberOrdersBean = memberOrdersService.update(orderID,1);
		if(memberOrdersBean!=null) {
			return ApiResponse.success(memberOrdersBean);
		}
		return ApiResponse.fail(400,"error","MemberOrdersController.updateMemberReturn false,id or store can't be null");
	}
	
	//修改個別order(客戶已點擊還書,店家確認)
		@PutMapping("/update/store/{orderID}")
		public ApiResponse<Object> updateStoreReturn(@PathVariable Integer orderID) {
			if(memberOrdersService.findBookIdAndQuantityToReturn(orderID)==false) {
				return ApiResponse.fail(400, "error", "MemberOrdersController.updateStoreReturn false,orderID shouldn't be null");
			}
			MemberOrdersBean memberOrdersBean = memberOrdersService.update(orderID,2);
			if(memberOrdersBean!=null) {
				return ApiResponse.success(memberOrdersBean);
			}
			return ApiResponse.fail(400,"error","MemberOrdersController.updateStoreReturn false");
		}
	
	
}
