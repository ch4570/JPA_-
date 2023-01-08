package jpaboard.jpaproject.controller;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


    @DeleteMapping("/user/delete/{userNo}")
    public void removeUser(@PathVariable("userNo") Long userNo) {

        userService.removeUser(userNo);
    }



}

