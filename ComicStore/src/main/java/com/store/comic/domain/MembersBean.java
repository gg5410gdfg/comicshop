package com.store.comic.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
@Table(name = "Members")
public class MembersBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "telePhone")
	private String telePhone;
	@Column(name = "createTime")
	private Timestamp createTime;
	@Column(name = "updateTime")
	private Timestamp updateTime;
	
	@JsonManagedReference("members-membersOrder")
	@OneToMany(mappedBy = "membersBean",cascade = CascadeType.ALL)
	private List<MemberOrdersBean> memberOredersBeans=new ArrayList<>();
}
