package com.store.comic.service;



import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import org.springframework.stereotype.Service;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.MemberOrdersBean;
import com.store.comic.domain.MembersBean;
import com.store.comic.dto.BookAndOrderDto;
import com.store.comic.repository.BooksRepository;
import com.store.comic.repository.MemberOrdersRepository;
import com.store.comic.repository.MembersRepository;



@Service
public class MemberOrdersService {
	@Autowired
	private MemberOrdersRepository memberOrdersRepository;
	@Autowired
	private MembersRepository membersRepository;
	@Autowired
	private BooksRepository booksRepository;
	@Autowired
	private MemberOrdersService memberOrdersService;
	@Autowired
	private BooksService booksService;
	public MemberOrdersBean insert(Integer bookId,Integer memberId) {
		MemberOrdersBean memberOredersBean=new MemberOrdersBean();
			Timestamp timestamp = Timestamp.from(Instant.now());
			if(memberId!=null && bookId!=null) {
				Optional<MembersBean> memberOptional = membersRepository.findById(memberId);
				Optional<BooksBean> bookOptional = booksRepository.findById(bookId);
				if(memberOptional.isPresent() && bookOptional.isPresent()) {
					memberOredersBean.setTradeTime(timestamp);
					memberOredersBean.setCreateTime(timestamp);
					memberOredersBean.setUpdateTime(timestamp);
					memberOredersBean.setMembersBean(memberOptional.get());
					memberOredersBean.setBooksBean(bookOptional.get());
					memberOredersBean.setCheckReturn(0);
					System.out.println("memberOredersBean.getCheckReturn()");
					System.out.println(memberOredersBean.getCheckReturn());
					return memberOrdersRepository.save(memberOredersBean);
					
				}		
			}
		return null;
	}
	
	//修改訂單(是否已還書)
	public MemberOrdersBean update(Integer orderID,Integer condition) {
			if(orderID!=null && condition!=null) {
				Optional<MemberOrdersBean> optional = memberOrdersRepository.findById(orderID);
				if(optional.isPresent()) {
					MemberOrdersBean memberOrdersBean = optional.get();
					memberOrdersBean.setCheckReturn(condition);;
					Timestamp timestamp = Timestamp.from(Instant.now());
					memberOrdersBean.setUpdateTime(timestamp);
					return memberOrdersRepository.save(memberOrdersBean);
				}
			}
		
		return null;
	}
	
	//還書回到商店庫存邏輯
	public Boolean findBookIdAndQuantityToReturn(Integer orderID) {
		if(orderID!=null) {
			Optional<MemberOrdersBean> optional = memberOrdersRepository.findById(orderID);
			if(optional.isPresent()) {
				MemberOrdersBean memberOrdersBean = optional.get();
				Integer bookId = memberOrdersBean.getBooksBean().getId();
				Integer quantity = memberOrdersBean.getMemberOrderDetailsBean().getQuantity();
				booksService.updateByOrder(bookId,quantity,"back");
				return true;
			}
		}
		return false;
	}
	

	public List<BookAndOrderDto> find(Integer memberId) {
		if(memberId!=null) {
			List<BookAndOrderDto> bookAndOrderDtos = new ArrayList<BookAndOrderDto>();
			List<MemberOrdersBean> ordersByMemberId = memberOrdersRepository.findOrdersByMemberId(memberId);
			for (MemberOrdersBean memberOrdersBean : ordersByMemberId) {
				BookAndOrderDto bookAndOrderDto = new BookAndOrderDto();
				bookAndOrderDto.setId(memberOrdersBean.getId());
				bookAndOrderDto.setMemberId(memberOrdersBean.getMembersBean().getId());
				bookAndOrderDto.setTradeTime(memberOrdersBean.getTradeTime());
				bookAndOrderDto.setCheckReturn(memberOrdersBean.getCheckReturn());
				bookAndOrderDto.setBooksBean(memberOrdersBean.getBooksBean());
				bookAndOrderDto.setMemberOrderDetailsBean(memberOrdersBean.getMemberOrderDetailsBean());
				bookAndOrderDtos.add(bookAndOrderDto);
			}
			
			return bookAndOrderDtos;
		}
		return null;
	}
	
	public List<BookAndOrderDto> findAll() {
		List<BookAndOrderDto> bookAndOrderDtos = new ArrayList<BookAndOrderDto>();
		List<MemberOrdersBean> ordersByMemberId = memberOrdersRepository.findAll();
		for (MemberOrdersBean memberOrdersBean : ordersByMemberId) {
			BookAndOrderDto bookAndOrderDto = new BookAndOrderDto();
			bookAndOrderDto.setId(memberOrdersBean.getId());
			bookAndOrderDto.setMemberId(memberOrdersBean.getMembersBean().getId());
			bookAndOrderDto.setTradeTime(memberOrdersBean.getTradeTime());
			bookAndOrderDto.setCheckReturn(memberOrdersBean.getCheckReturn());
			bookAndOrderDto.setBooksBean(memberOrdersBean.getBooksBean());
			bookAndOrderDto.setMemberOrderDetailsBean(memberOrdersBean.getMemberOrderDetailsBean());
			bookAndOrderDtos.add(bookAndOrderDto);
		}
		return bookAndOrderDtos;

	}
}
