package com.ensaf.haron.belghit.repository;

import com.ensaf.haron.belghit.repository.entity.Order;
import com.ensaf.haron.belghit.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
