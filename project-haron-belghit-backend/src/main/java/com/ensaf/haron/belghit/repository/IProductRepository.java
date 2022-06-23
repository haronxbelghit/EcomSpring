package com.ensaf.haron.belghit.repository;

import com.ensaf.haron.belghit.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long > {
}
