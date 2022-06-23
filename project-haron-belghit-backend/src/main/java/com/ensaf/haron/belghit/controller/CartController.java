package com.ensaf.haron.belghit.controller;

import com.ensaf.haron.belghit.common.ApiResponse;
import com.ensaf.haron.belghit.dto.cart.AddToCardDto;
import com.ensaf.haron.belghit.dto.cart.CartDto;
import com.ensaf.haron.belghit.exceptions.CartItemNotExistException;
import com.ensaf.haron.belghit.exceptions.AuthenticationFailException;
import com.ensaf.haron.belghit.exceptions.ProductNotExistException;
import com.ensaf.haron.belghit.repository.entity.Product;
import com.ensaf.haron.belghit.repository.entity.User;
import com.ensaf.haron.belghit.service.AuthenticationService;
import com.ensaf.haron.belghit.service.CartService;
import com.ensaf.haron.belghit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCardDto addToCartDto,
                                                 @RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        Product product = productService.getProductById(addToCartDto.getProductId());
        System.out.println("Product to add "+  product.getName());
        cartService.addToCart(addToCartDto, product, user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart."), HttpStatus.CREATED);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@Valid @RequestBody AddToCardDto cartDto,
                                                      @RequestParam("token") String token) throws AuthenticationFailException,ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.updateCartItem(cartDto, user);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId,@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed."), HttpStatus.OK);
    }
}
