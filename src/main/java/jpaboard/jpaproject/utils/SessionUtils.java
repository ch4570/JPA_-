package jpaboard.jpaproject.utils;

import jpaboard.jpaproject.dto.UserLoginRequestDto;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {


    /*
    *   회원 로그인시 Session 등록
    *   @param UserLoginRequestDto
    * */
    public static void addUserAttribute(UserLoginRequestDto dto) {

        // Session을 얻어온다.
        HttpSession session = getSession();

        // Session에 회원 정보 등록
        session.setAttribute("loginUser", dto);

    }

    /*
    *   Session에 등록된 회원 아이디를 반환 - (등록자 & 수정자ID 셋팅용)
    *   @return String(userId)
    * */
    public static String getUserId() {

        // Session을 얻어온다.
        HttpSession session = getSession();

        // Session에서 로그인한 회원의 정보를 얻어온다.
        UserLoginRequestDto userDto = (UserLoginRequestDto)session.getAttribute("loginUser");

        // User의 객체에서 ID만 반환한다.
        return userDto.getId();
    }



    /*
    *   로그아웃시 사용할 세션 끊기 메서드
    * */
    public static void invalidateSession() {

        // Session을 얻어온다.
        HttpSession session = getSession();

        // Session을 끊는다.
        session.invalidate();
    }


    /*
    *   Session을 얻어오는 로직
    *   @return HttpSession
    * */
    public static HttpSession getSession() {
        return (HttpSession) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }


}
