package com.ensaf.haron.belghit.repository;

import com.ensaf.haron.belghit.repository.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
