package jpaboard.jpaproject.controller;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.dto.UserJoinRequestDto;
import jpaboard.jpaproject.dto.UserLoginRequestDto;
import jpaboard.jpaproject.dto.UserResponseDto;
import jpaboard.jpaproject.service.UserService;
import jpaboard.jpaproject.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;

    /*
     *   회원 조회
     *   @param Model
     *   @param userNo
     *   @return String(View-Name)
     * */
    @GetMapping("/users/{userNo}")
    public String findUser(@PathVariable("userNo") Long userNo, Model model) {

        // 회원 한명 조회
        User user = userService.findOneUser(userNo);

        // Entity => DTO변환
        UserResponseDto responseUser = new UserResponseDto(user);

        model.addAttribute("user", responseUser);

        return "user/userInfo";
    }

    /*
    *   회원가입 Form 띄우기
    *   @param Model
    *   @param UserJoinRequestDto
    *   @return String(View-Name)
    * */
    @GetMapping("/users/joinForm")
    public String userJoinForm(Model model) {
        model.addAttribute("userJoinRequestDto", new UserJoinRequestDto());
        return "joinForm";
    }

    /*
    *   로그인 Form 띄우기
    *   @param Model
    *   @return String(View-Name)
    * */
    @GetMapping("/users/loginForm")
    public String userLoginForm(Model model) {
        model.addAttribute("userLoginRequestDto", new UserLoginRequestDto());
        return "loginForm";
    }

    /*
    *   로그인
    *   @param UserLoginRequestDto
    *   @param HttpServletResponse
    *   @return String(View-Name)
    * */
    @PostMapping("/users/login")
    public String userLogin(@Valid UserLoginRequestDto userLoginRequestDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        // 유효성 검사후 입력된 필드에 문제가 있을시 로그인 페이지로 다시 이동
        if(bindingResult.hasErrors()) {
            return "loginForm";
        }

        // 입력된 아이디와 패스워드가 회원정보와 일치하는지 조회
        boolean isAuthorization = userService.isAuthorization(userLoginRequestDto);

        // 입력된 정보가 일치하면 -> 메인페이지 이동
        if(isAuthorization) {
            // 로그인한 유저의 정보를 전달한다.
            SessionUtils.addUserAttribute(userLoginRequestDto);

            // 메인페이지 이동
            return "redirect:/";

            // 일치하지 않으면 -> 로그인 페이지로 redirect
        } else {
            redirectAttributes.addFlashAttribute("error_msg", "아이디 또는 비밀번호가 잘못되었습니다");
            return "redirect:/users/loginForm";
        }


    }


    /*
     *   회원 가입
     *   @param UserJoinRequestDto
     *   @return
     * */
    @PostMapping("/users")
    public String joinUser(@Valid UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {

        // 입력한 필드에 유효성을 위반한 항목이있다면 회원가입 페이지로 다시 이동
        if(bindingResult.hasErrors()) {
            return "joinForm";
        }

        // DTO => Entity 변환
        User joinUser = userJoinRequestDto.userRequestToEntity();

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
    @GetMapping("/users/modifyForm/{userNo}")
    public String modifyUserForm(@PathVariable("userNo") Long userNo, Model model) {
        // 회원 한명 조회
        User user = userService.findOneUser(userNo);

        // Entity => DTO 변환
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(user);

        model.addAttribute("userRequestDto", userJoinRequestDto);

        return "";
    }

    /*
     *   회원 수정
     *   @param UserJoinRequestDto
     *   @return ResponseEntity
     * */
    @PatchMapping ("/users/{userNo}")
    public String modifyUser(@PathVariable("userNo") Long userNo,
                             @Valid UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "";
        }

        // DTO => Entity 변환
        User modifyUser = userJoinRequestDto.userRequestToEntity();

         // 회원정보 수정 진행
        User modifiedUser = userService.modifyUser(modifyUser);

        return "";
    }

    @GetMapping("/users")
    public String findAllUser(Model model) {
        // 전체 회원 조회(Entity List 반환)
        List<User> userList = userService.findAllUsers();

        // 전체 회원 리스트(Entity) => DTO로 변환
        List<UserResponseDto> userResponseDtoList = userList.stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());

        model.addAttribute("userList", userResponseDtoList);

        return "userList";
    }

    @GetMapping("user/delete")
    public String deleteUser() {
        return "delete";
    }

}
