package jpaboard.jpaproject.utils;

import jpaboard.jpaproject.dto.UserLoginRequestDto;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {


    public static void addUserAttribute(UserLoginRequestDto dto) {

        // Session을 얻어온다.
        HttpSession session = (HttpSession)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        // Session에 회원 정보 등록
        session.setAttribute("loginUser", dto);

    }
}
