package com.store.comic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.comic.domain.MembersBean;


public interface MembersRepository extends JpaRepository<MembersBean, Integer> {

}
