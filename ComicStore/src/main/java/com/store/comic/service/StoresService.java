package com.store.comic.service;



import java.sql.Timestamp;
import java.time.Instant;
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

import com.store.comic.domain.StoresBean;
import com.store.comic.repository.StoreRepository;


@Service
public class StoresService {
	@Autowired
	private StoreRepository storeRepository;
	
	public StoresBean insert(String string) {
		StoresBean storeBean=new StoresBean();
		try {
			JSONObject json=new JSONObject(string);

			String name=json.isNull("name")? null:json.getString("name");
			String telePhone=json.isNull("telePhone")? null:json.getString("telePhone");
			String address=json.isNull("address")? null:json.getString("address");
			Timestamp timestamp = Timestamp.from(Instant.now());
			storeBean.setName(name);
			storeBean.setTelePhone(telePhone);
			storeBean.setAddress(address);
			storeBean.setCreateTime(timestamp);
			storeBean.setUpdateTime(timestamp);
			return storeRepository.save(storeBean);
		} catch (JSONException e) {
			System.out.println("StoreService.insert:JSONException error");
		}
		return null;
	}
	
	public StoresBean update(String string) {
		try {
			JSONObject json = new JSONObject(string);
			Integer id = json.isNull("id") ? null : json.getInt("id");
			String name = json.isNull("name") ? null : json.getString("name");
			String telePhone = json.isNull("telePhone") ? null : json.getString("telePhone");
			String address = json.isNull("address") ? null : json.getString("address");
			

			if(id!=null) {
				Optional<StoresBean> optional = storeRepository.findById(id);
				if(optional.isPresent()) {
					StoresBean storesBean = optional.get();
					storesBean.setName(name);
					storesBean.setTelePhone(telePhone);
					Timestamp timestamp = Timestamp.from(Instant.now());
					storesBean.setUpdateTime(timestamp);
					storesBean.setAddress(address);
					return storeRepository.save(storesBean);
				}
			}
		} catch (JSONException e) {
			System.out.println("StoreService.insert:update error");
		}
		return null;
	}
	
	public boolean delete(Integer id) {
		if(id!=null && storeRepository.existsById(id)) {
			storeRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public StoresBean findById(Integer id) {
		if(id!=null) {
			Optional<StoresBean> optional = storeRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public List<StoresBean> find() {
		List<StoresBean> storesBeans = storeRepository.findAll();
		return storesBeans;
	}
}
