package com.store.comic.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@Entity
@Table(name = "MemberOrders")
public class MemberOrdersBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name = "tradeTime")
	private Timestamp tradeTime;
	@Column(name = "checkReturn")
	private Integer checkReturn;
	@Column(name = "createTime")
	private Timestamp createTime;
	@Column(name = "updateTime")
	private Timestamp updateTime;
	
	@JsonBackReference("members-membersOrder")
	@ManyToOne
	@JoinColumn(
			insertable = true,
			updatable = true,
			name="memberId",
			referencedColumnName = "id"
	)
	private MembersBean membersBean;
	@JsonBackReference("books-membersOrder")
	@ManyToOne
	@JoinColumn(
			insertable = true,
			updatable = true,
			name="bookId",
			referencedColumnName = "id"
	)
	private BooksBean booksBean;
	@JsonManagedReference("memberOredersBean-memberOrderDetails")
	@OneToOne(mappedBy = "memberOredersBean",cascade = CascadeType.ALL)
	private MemberOrderDetailsBean memberOrderDetailsBean;
}
