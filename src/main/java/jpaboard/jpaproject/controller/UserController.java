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

        // 회원 한명 조회
        User user = userService.findOneUser(userNo);

        // Entity => DTO변환
        UserResponseDto responseUser = new UserResponseDto(user);

        model.addAttribute("user", responseUser);

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
    public String joinUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "";
        }

        // DTO => Entity 변환
        User joinUser = userRequestDto.userRequestToEntity();

        // 회원 가입진행
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
        // 회원 한명 조회
        User user = userService.findOneUser(userNo);

        // Entity => DTO 변환
        UserRequestDto userRequestDto = new UserRequestDto(user);

        model.addAttribute("userRequestDto", userRequestDto);

        return "";
    }

    /*
     *   회원 수정
     *   @param UserRequestDto
     *   @return ResponseEntity
     * */
    @PutMapping("/user/modify")
    public String modifyUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "";
        }

        // DTO => Entity 변환
        User modifyUser = userRequestDto.userRequestToEntity();

         // 회원정보 수정 진행
        User modifiedUser = userService.modifyUser(modifyUser);

        return "";
    }

    @GetMapping("user/delete")
    public String deleteUser() {
        return "delete";
    }

}
