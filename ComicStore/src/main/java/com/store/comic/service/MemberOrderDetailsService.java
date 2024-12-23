package com.store.comic.service;



import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import org.springframework.stereotype.Service;

import com.store.comic.domain.BooksBean;
import com.store.comic.domain.MemberOrderDetailsBean;
import com.store.comic.domain.MemberOrdersBean;
import com.store.comic.domain.MembersBean;
import com.store.comic.repository.BooksRepository;
import com.store.comic.repository.MemberOrdersDetailsRepository;
import com.store.comic.repository.MemberOrdersRepository;
import com.store.comic.repository.MembersRepository;



@Service
public class MemberOrderDetailsService {
	@Autowired
	private MemberOrdersRepository memberOrdersRepository;
	@Autowired
	private MemberOrdersDetailsRepository memberOrdersDetailsRepository;
	@Autowired
	private BooksRepository booksRepository;
	//用orderId來新增
	public MemberOrderDetailsBean insert(Integer bookId,Integer quantity,Integer orderId) {
		MemberOrderDetailsBean memberOrderDetailsBean=new MemberOrderDetailsBean();
				if(orderId!=null && bookId!=null) {
					Optional<MemberOrdersBean> memberOrdersOptional = memberOrdersRepository.findById(orderId);
					Optional<BooksBean> bookOptional = booksRepository.findById(bookId);
					if(memberOrdersOptional.isPresent() && bookOptional.isPresent()) {
						memberOrderDetailsBean.setQuantity(quantity);
						memberOrderDetailsBean.setMemberOredersBean(memberOrdersOptional.get());;
						memberOrderDetailsBean.setBooksBean(bookOptional.get());
						return memberOrdersDetailsRepository.save(memberOrderDetailsBean);				
				}			
			}
		return null;
	}

}
