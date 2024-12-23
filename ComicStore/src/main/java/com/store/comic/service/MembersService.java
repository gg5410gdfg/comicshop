package com.store.comic.service;



import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import org.springframework.stereotype.Service;

import com.store.comic.domain.MembersBean;
import com.store.comic.repository.MembersRepository;



@Service
public class MembersService {
	@Autowired
	private MembersRepository membersRepository;
	
	public MembersBean insert(String string) {
		MembersBean membersBean=new MembersBean();
		try {
			JSONObject json=new JSONObject(string);

			String name=json.isNull("name")? null:json.getString("name");
			String telePhone=json.isNull("telePhone")? null:json.getString("telePhone");
			Timestamp timestamp = Timestamp.from(Instant.now());
			membersBean.setName(name);
			membersBean.setTelePhone(telePhone);
			membersBean.setCreateTime(timestamp);
			membersBean.setUpdateTime(timestamp);
			return membersRepository.save(membersBean);
		} catch (JSONException e) {
			System.out.println("StoreService.insert:JSONException error");
		}
		return null;
	}
	
	public MembersBean update(String string) {
		try {
			JSONObject json = new JSONObject(string);
			Integer id = json.isNull("id") ? null : json.getInt("id");
			String name = json.isNull("name") ? null : json.getString("name");
			String telePhone = json.isNull("telePhone") ? null : json.getString("telePhone");
			

			if(id!=null) {
				Optional<MembersBean> optional = membersRepository.findById(id);
				if(optional.isPresent()) {
					MembersBean MembersBean = optional.get();
					MembersBean.setName(name);
					MembersBean.setTelePhone(telePhone);
					Timestamp timestamp = Timestamp.from(Instant.now());
					MembersBean.setUpdateTime(timestamp);
					return membersRepository.save(MembersBean);
				}
			}
		} catch (JSONException e) {
			System.out.println("StoreService.insert:update error");
		}
		return null;
	}
	
	public boolean delete(Integer id) {
		if(id!=null && membersRepository.existsById(id)) {
			membersRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public MembersBean findById(Integer id) {
		if(id!=null) {
			Optional<MembersBean> optional = membersRepository.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public List<MembersBean> find() {
		List<MembersBean> MembersBeans = membersRepository.findAll();
		return MembersBeans;
	}
}
