package com.ensaf.haron.belghit.repository;

import com.ensaf.haron.belghit.repository.entity.Cart;
import com.ensaf.haron.belghit.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    List<Cart> deleteByUser(User user);
}
