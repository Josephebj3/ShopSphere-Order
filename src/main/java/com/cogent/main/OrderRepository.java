package com.cogent.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>
{

	@Query(value = "from OrderEntity where userId = :userId ORDER BY orderId")
	List<OrderEntity> getByUserId(int userId);

	List<OrderEntity> findByOrderId(int orderId);

	List<OrderEntity> findByUserId(int userId);

	void deleteByUserId(int userId);

	@Query(value = "from OrderEntity WHERE userId = :userId AND orderId = :orderId ORDER BY orderId")
	List<OrderEntity> findByOrderIdAndUserId(int userId, int orderId);
	
}
