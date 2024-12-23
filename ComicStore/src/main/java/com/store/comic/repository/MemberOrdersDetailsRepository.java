package com.store.comic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.comic.domain.MemberOrderDetailsBean;



public interface MemberOrdersDetailsRepository extends JpaRepository<MemberOrderDetailsBean, Integer> {

}
