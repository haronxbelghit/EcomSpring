package com.ensaf.haron.belghit.controller;

import com.ensaf.haron.belghit.common.ApiResponse;
import com.ensaf.haron.belghit.dto.stripe.CheckoutItemDto;
import com.ensaf.haron.belghit.dto.stripe.StripeResponse;
import com.ensaf.haron.belghit.service.OrderService;
import com.ensaf.haron.belghit.exceptions.AuthenticationFailException;
import com.ensaf.haron.belghit.exceptions.OrderNotFoundException;
import com.ensaf.haron.belghit.repository.entity.Order;
import com.ensaf.haron.belghit.repository.entity.User;
import com.ensaf.haron.belghit.service.AuthenticationService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PostMapping("/checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {

        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
