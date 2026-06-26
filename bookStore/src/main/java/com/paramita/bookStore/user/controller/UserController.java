package com.paramita.bookStore.user.controller;

import com.paramita.bookStore.user.TO.LoginReqTo;
import com.paramita.bookStore.user.TO.UserTo;
import com.paramita.bookStore.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReqTo loginReqTo){
        return ResponseEntity.ok(userService.login(loginReqTo));
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserTo> register(@RequestBody @Valid UserTo user) {
    	return ResponseEntity.ok(this.userService.register(user));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserTo>> getUserList(){
       return ResponseEntity.ok(userService.getUserList());
    }
}
