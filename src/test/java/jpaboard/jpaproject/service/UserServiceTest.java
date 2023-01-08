package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

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
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
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
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .userRole(UserRole.USER)
                .build();

        insertUser = userService.join(insertUser);

        User modifyUser = User.builder()
                .no(insertUser.getNo())
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("DevSeo")
                .userRole(UserRole.USER)
                .build();

        // when
        User modifiedUser = userService.modifyUser(modifyUser);
        User findModifiedUser = userService.findOneUserByName("DevSeo");

        //then
        assertThat(modifiedUser).isEqualTo(findModifiedUser);

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void deleteUserTest() {

        // given
        User insertUser = userService.join(User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .userRole(UserRole.USER)
                .build());

        // when
        userService.removeUser(insertUser.getNo());

        // then
        assertThatThrownBy(() -> userService.findOneUser(insertUser.getNo()))
                .isInstanceOf(IllegalStateException.class);
    }

}