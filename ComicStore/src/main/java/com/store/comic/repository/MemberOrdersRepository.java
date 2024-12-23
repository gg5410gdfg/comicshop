package com.store.comic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.store.comic.domain.MemberOrdersBean;

public interface MemberOrdersRepository extends JpaRepository<MemberOrdersBean, Integer> {
	@Query(value="SELECT * FROM MemberOrders WHERE memberId = :memberId",nativeQuery = true)
    List<MemberOrdersBean> findOrdersByMemberId(@Param("memberId") Integer memberId);
}
