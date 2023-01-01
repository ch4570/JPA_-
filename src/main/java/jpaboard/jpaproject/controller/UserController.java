package jpaboard.jpaproject.controller;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.dto.UserRequestDto;
import jpaboard.jpaproject.dto.UserResponseDto;
import jpaboard.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @GetMapping("/user/find/{userNo}")
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

    /*
    *   회원 가입 Form 띄우기
    *   @param Model
    *   @param UserRequestDto
    *   @return String(View-Name)
    * */
    @GetMapping("/user/join")
    public String userJoinForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "";
    }

    /*
     *   회원 가입
     *   @param UserRequestDto
     *   @return ResponseEntity
     * */
    @PostMapping("/user/join")
    public String joinUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "";
        }

        User joinUser = userRequestDto.userRequestToEntity();
        User userResponse = userService.join(joinUser);

        return "redirect:/";
    }


    /*
     *   회원 수정 Form 띄우기
     *   @param Model
     *   @param userNo
     *   @return String(View-Name)
     * */
    @GetMapping("/user/modify/{userNo}")
    public String modifyUserForm(@PathVariable("userNo") Long userNo, Model model) {
        User user = userService.findOneUser(userNo);
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .no(user.getNo())
                .id(user.getId())
                .name(user.getName())
                .pwd(user.getPwd())
                .email(user.getEmail())
                .build();

        model.addAttribute("userRequestDto", userRequestDto);
        return "";
    }

    /*
     *   회원 수정
     *   @param UserRequestDto
     *   @return ResponseEntity
     * */
    @PutMapping("/user/modify")
    public String modifyUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "";
        }

        User modifyUser = userRequestDto.userRequestToEntity();
        User modifiedUser = userService.modifyUser(modifyUser);

        return "";
    }

}
