package com.store.comic.domain;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//@Converter(autoApply = true)
@Setter
@Getter
@Entity
@Table(name = "Books")
public class BooksBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "totalQuantity")
	private Integer totalQuantity;
	@Column(name = "nowQuantity")
	private Integer nowQuantity;
	@Column(name = "createTime")
	private Timestamp createTime;
	@Column(name = "updateTime")
	private Timestamp updateTime;
	@Column(name = "mimeType")
	private String mimeType;
	@Column(name = "photo")
	private byte[] photo;
	@Column(name = "introduce")
	private String introduce;
	@Column(name = "author")
	private String author;
	@Column(name = "publicationDate")
	private Date publicationDate;
	@Column(name="price")
	private Integer price;
	@Column(name="checkCondition",columnDefinition = "bit")
	private Boolean checkCondition;
	
	@JsonBackReference("books-store")
	@ManyToOne
	@JoinColumn(
			insertable = true, 
			updatable = true,
			name = "storeId",
			referencedColumnName = "id"
	)
	private StoresBean store;
	@JsonManagedReference("books-membersOrder")
	@OneToMany(mappedBy = "booksBean",cascade = CascadeType.ALL)
	private List<MemberOrdersBean> memberOredersBeans=new ArrayList<>();
	@JsonManagedReference("books-memberOrderDetails")
	@OneToMany(mappedBy = "booksBean",cascade = CascadeType.ALL)
	private List<MemberOrderDetailsBean> memberOrderDetailsBeans=new ArrayList<>();

	
}
