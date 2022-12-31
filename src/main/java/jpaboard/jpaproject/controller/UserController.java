package jpaboard.jpaproject.controller;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.dto.UserResponseDto;
import jpaboard.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
     *   회원 조회
     *   @param Model
     *   @param userNo
     *   @return String(View-Name)
     * */
    @GetMapping("/user/{userNo}")
    public String findUser(@PathVariable("userNo") Long userNo, Model model) {
        User user = userService.findOneUser(userNo);
        UserResponseDto responseUser = UserResponseDto.builder()
                        .no(user.getNo())
                        .name(user.getName())
                        .pwd(user.getPwd())
                        .id(user.getId())
                        .email(user.getEmail())
                        .userRole(user.getUserRole())
                        .build();

        model.addAttribute("user", responseUser);
        System.out.println(responseUser);
        return "user/userInfo";
    }
}
