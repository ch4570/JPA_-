package jpaboard.jpaproject.utils;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import jpaboard.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final UserService userService;

//    @PostConstruct
    public void init() {
        User insertUser1 = User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .userRole(UserRole.USER)
                .build();

        User insertUser2 = User.builder()
                .id("Redis")
                .pwd("react")
                .email("bbb@bbb.com")
                .name("Devseo")
                .userRole(UserRole.USER)
                .build();

        User insertUser3 = User.builder()
                .id("ElasticSearch")
                .pwd("Vue.js")
                .email("ccc@ccc.com")
                .name("Hodong")
                .userRole(UserRole.USER)
                .build();

        userService.join(insertUser1);
        userService.join(insertUser2);
        userService.join(insertUser3);
    }
}
