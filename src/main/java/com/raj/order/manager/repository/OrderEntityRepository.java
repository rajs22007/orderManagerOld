package com.raj.order.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.order.manager.domain.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {

}
