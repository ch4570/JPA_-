package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;

    @BeforeEach
    public void beforeTest() {
        userService.removeAllUsers();
    }

    @AfterEach
    public void afterTest() {
        userService.removeAllUsers();
    }



    @Test
    @DisplayName("정상 회원 가입 테스트")
    @Transactional
    public void joinUser() {

        // given
        User insertUser = userService.join(User.builder()
                .id("Rust")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C언어의 정석")
                .userRole(UserRole.USER)
                .build());

        // when
        User findUser = userService.findOneUser(insertUser.getNo());

        // then
        assertThat(insertUser).isEqualTo(findUser);

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    @Transactional
    public void joinDuplicateUser() {

        // given
        User insertUser = User.builder()
                .id("Rust")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C언어의 정석")
                .userRole(UserRole.USER)
                .build();

        // when
        userService.join(insertUser);

        // then
        assertThatThrownBy(() -> userService.join(insertUser))
                .isInstanceOf(IllegalStateException.class);

    }

    @Test
    @DisplayName("회원 수정 테스트")
    @Transactional
    public void modifyUserTest() {

        // given
        User insertUser = User.builder()
                .id("Rust")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C언어의 정석")
                .userRole(UserRole.USER)
                .build();

        insertUser = userService.join(insertUser);

        User modifyUser = User.builder()
                .no(insertUser.getNo())
                .id("RabbitMQ")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("HodongDev")
                .userRole(UserRole.USER)
                .build();

        // when
        User modifiedUser = userService.modifyUser(modifyUser);
        User findModifiedUser = userService.findOneUserByName("HodongDev");

        //then
        assertThat(modifiedUser).isEqualTo(findModifiedUser);

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void deleteUserTest() {

        // given
        User insertUser = User.builder()
                .id("Rust")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C언어의 정석")
                .userRole(UserRole.USER)
                .build();
        userService.join(insertUser);

        // when
        userService.removeUser(insertUser.getNo());

        // then
        assertThatThrownBy(() -> userService.findOneUser(insertUser.getNo()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("회원 리스트 조회 테스트")
    @Transactional
    public void selectAllUsers() {
        // given
        userService.removeAllUsers();
        User insertUser1 = userService.join(User.builder()
                .id("Ruby on Rails")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("A")
                .userRole(UserRole.USER)
                .build());


        User insertUser2 = userService.join(User.builder()
                .id("RedisCacheManager")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("B")
                .userRole(UserRole.USER)
                .build());


        User insertUser3 = userService.join(User.builder()
                .id("Docker")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C")
                .userRole(UserRole.USER)
                .build());


        // when
        List<User> userList = userService.findAllUsers();

        // then
        assertThat(insertUser1).isEqualTo(userList.get(0));
        assertThat(insertUser2).isEqualTo(userList.get(1));
        assertThat(insertUser3).isEqualTo(userList.get(2));
    }

    @Test
    @DisplayName("회원 리스트 비어 있을때 예외 발생 여부 테스트")
    @Transactional
    public void selectAllUsersIsEmpty() {
        // given
        userService.removeAllUsers();

        // when
        AbstractThrowableAssert throwable = assertThatThrownBy(() -> userService.findAllUsers());

        // then
       throwable.isInstanceOf(IllegalStateException.class);
    }
}