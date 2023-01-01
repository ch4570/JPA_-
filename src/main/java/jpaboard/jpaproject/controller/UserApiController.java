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


    @DeleteMapping("user/delete/{userNo}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable("userNo") Long userNo) throws Exception{
        User user = userService.findOneUser(userNo);

        userService.removeUser(user);
        return ResponseEntity.ok().build();
    }

}

