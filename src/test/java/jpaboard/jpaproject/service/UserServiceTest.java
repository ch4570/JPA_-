package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;

    @Test
    @DisplayName("정상 회원 가입 테스트")
    @Transactional
    public void joinUser() {

        // given
        User insertUser = userService.join(User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .build());

        // when
        User findUser = userService.findOneUser(insertUser.getNo()).get();

        // then
        assertEquals(insertUser, findUser);

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    @Transactional
    public void joinDuplicateUser() {

        // given
        User insertUser = User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .build();

        // when
        userService.join(insertUser);

        // then
        Throwable exception = assertThrows(IllegalStateException.class,
                () -> userService.join(insertUser));

        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("회원 수정 테스트")
    @Transactional
    public void modifyUserTest() {

        // given
        User insertUser = User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .build();

        insertUser = userService.join(insertUser);

        // when
        User modifiedUser = userService.modifyUser(insertUser, "bbb@bbb.com",
               "DevSeo", "React", "Java");

        User findModifiedUser = userService.findOneUserByName("DevSeo");

        assertEquals(modifiedUser, findModifiedUser);

    }

}