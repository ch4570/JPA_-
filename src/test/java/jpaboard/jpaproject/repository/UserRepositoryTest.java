package jpaboard.jpaproject.repository;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import jpaboard.jpaproject.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    public void beforeTest() {
        userRepository.deleteAll();
    }

    @AfterEach
    public void afterTest() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 회원 가입 케이스 테스트")
    @Transactional
    public void joinUser() {
        // given
        User insertUser = userRepository.save(User.builder()
                        .id("Kafka")
                        .pwd("javascript")
                        .email("aaa@aaa.com")
                        .name("RexSeo")
                        .userRole(UserRole.USER)
                        .build());

        // when
        User saveUser = userRepository.findById(insertUser.getNo()).get();

        // then
        assertThat(insertUser).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("회원 이름으로 조회 테스트")
    @Transactional
    public void findUserByName() {
        // given
        User insertUser = userRepository.save(User.builder()
                    .id("Kafka")
                    .pwd("javascript")
                    .email("aaa@aaa.com")
                    .name("RexSeo")
                    .userRole(UserRole.USER)
                    .build());

        // when
        User findUser = userRepository.findByName("RexSeo");

        // then
        assertThat(insertUser).isEqualTo(findUser);
    }


    @Test
    @DisplayName("없는 회원 조회 예외 발생 여부 테스트")
    @Transactional
    public void notExistUserFindTest() {

        // given
        Long userNo = 99L;

        // when
        Optional<User> findUser = userRepository.findById(userNo);

        // then
        assertThatThrownBy(() -> findUser.get())
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void deleteUserTest() {
        // given
        User insertUser = userRepository.save(User.builder()
                .id("Kafka")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("RexSeo")
                .userRole(UserRole.USER)
                .build());

        // when
        userRepository.delete(insertUser);
        Optional<User> findUser = userRepository.findById(insertUser.getNo());

        // then
        assertThatThrownBy(() -> findUser.get())
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("회원 리스트 조회 테스트")
    @Transactional
    public void selectAllUsers() {
        // given
        User insertUser1 = userRepository.save(User.builder()
                .id("Ruby on Rails")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("A")
                .userRole(UserRole.USER)
                .build());

        User insertUser2 = userRepository.save(User.builder()
                .id("RabbitMQ")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("B")
                .userRole(UserRole.USER)
                .build());

        User insertUser3 = userRepository.save(User.builder()
                .id("Docker")
                .pwd("javascript")
                .email("aaa@aaa.com")
                .name("C")
                .userRole(UserRole.USER)
                .build());

        // when
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "no"));

        // then
        assertThat(insertUser1).isEqualTo(userList.get(0));
        assertThat(insertUser2).isEqualTo(userList.get(1));
        assertThat(insertUser3).isEqualTo(userList.get(2));
    }

}