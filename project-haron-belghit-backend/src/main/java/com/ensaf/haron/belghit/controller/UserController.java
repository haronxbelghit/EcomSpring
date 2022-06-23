package com.ensaf.haron.belghit.controller;

import com.ensaf.haron.belghit.dto.ResponseDto;
import com.ensaf.haron.belghit.dto.user.RegisterDto;
import com.ensaf.haron.belghit.dto.user.SignInDto;
import com.ensaf.haron.belghit.dto.user.SignInResponseDto;
import com.ensaf.haron.belghit.exceptions.AuthenticationFailException;
import com.ensaf.haron.belghit.exceptions.CustomException;
import com.ensaf.haron.belghit.repository.IUserRepository;
import com.ensaf.haron.belghit.repository.entity.User;
import com.ensaf.haron.belghit.service.AuthenticationService;
import com.ensaf.haron.belghit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseDto register(@RequestBody RegisterDto registerDto)  throws CustomException {
        return userService.register(registerDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return  userService.signIn(signInDto);
    }
}