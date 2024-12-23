package com.store.comic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.comic.domain.StoresBean;

public interface StoreRepository extends JpaRepository<StoresBean, Integer> {

}
