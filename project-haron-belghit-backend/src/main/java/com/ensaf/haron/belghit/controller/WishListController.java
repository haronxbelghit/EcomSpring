package com.ensaf.haron.belghit.controller;

import com.ensaf.haron.belghit.common.ApiResponse;
import com.ensaf.haron.belghit.dto.product.ProductDto;
import com.ensaf.haron.belghit.exceptions.AuthenticationFailException;
import com.ensaf.haron.belghit.exceptions.WishtNotExistException;
import com.ensaf.haron.belghit.repository.entity.Product;
import com.ensaf.haron.belghit.repository.entity.User;
import com.ensaf.haron.belghit.repository.entity.Wish;
import com.ensaf.haron.belghit.service.AuthenticationService;
import com.ensaf.haron.belghit.service.ProductService;
import com.ensaf.haron.belghit.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        Long userId = authenticationService.getUser(token).getId();
        List<Wish> body = wishListService.readWishList(userId);
        List<ProductDto> products = new ArrayList<>();
        for (Wish wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user= authenticationService.getUser(token);

        Wish wishList = new Wish(user, product);
        wishListService.saveWishList(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wish list.");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{wishId}")
    public ResponseEntity<ApiResponse> deleteWish(@PathVariable("wishId") Integer wishId,@RequestParam("token") String token) throws AuthenticationFailException, WishtNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        wishListService.deleteWish(wishId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Wish has been removed."), HttpStatus.OK);
    }

}
