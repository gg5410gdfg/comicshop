package com.store.comic.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.store.comic.domain.BooksBean;


public interface BooksRepository extends JpaRepository<BooksBean, Integer> {
	@Query(value="SELECT * FROM Books WHERE storeId = :storeId",nativeQuery = true)
    List<BooksBean> findBooksByStoreId(@Param("storeId") Integer storeId);

}
