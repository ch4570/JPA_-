package jpaboard.jpaproject.controller;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.dto.UserRequestDto;
import jpaboard.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /*
    *   회원 가입
    *   @param UserRequestDto
    *   @return ResponseEntity
    * */
    @PostMapping("/user")
    public ResponseEntity<User> joinUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.internalServerError().build();
        }

        User joinUser = userRequestDto.userRequestToEntity();
        User userResponse = userService.join(joinUser);

        return ResponseEntity.ok()
                .body(joinUser);
    }

    /*
    *   회원 수정
    *   @param UserRequestDto
    *   @return ResponseEntity
    * */
    @PutMapping("/user")
    public ResponseEntity<User> modifyUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        User modifyUser = userRequestDto.userRequestToEntity();
        User modifiedUser = userService.modifyUser(modifyUser);

        return ResponseEntity.ok().body(modifiedUser);
    }
}

