package com.store.comic.domain;

import java.sql.Timestamp;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "MemberOrderDetails")
public class MemberOrderDetailsBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name = "quantity")
	private Integer quantity;
	
	@JsonBackReference("memberOredersBean-memberOrderDetails")
	@OneToOne
	@JoinColumn(
			insertable = true,
			updatable = true,
			name="orderId",
			referencedColumnName = "id"
	)
	private MemberOrdersBean memberOredersBean;
	@JsonBackReference("memberOredersBean-memberOrderDetails")
	@ManyToOne
	@JoinColumn(
			insertable = true,
			updatable = true,
			name="bookId",
			referencedColumnName = "id"
	)
	private BooksBean booksBean;
}
