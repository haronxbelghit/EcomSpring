package com.ensaf.haron.belghit.service;

import com.ensaf.haron.belghit.dto.product.ProductDto;
import com.ensaf.haron.belghit.exceptions.ProductNotExistException;
import com.ensaf.haron.belghit.repository.IProductRepository;
import com.ensaf.haron.belghit.repository.entity.Category;
import com.ensaf.haron.belghit.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    IProductRepository productRepository;

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    public void updateProduct(Long productId, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productId);
        productRepository.save(product);
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        return new Product(productDto, category);
    }

    public static ProductDto getDtoFromProduct(Product product) {
        return new ProductDto(product);
    }

    public Product getProductById(Long productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotExistException("Product id is invalid." + productId);
        return optionalProduct.get();
    }
}
