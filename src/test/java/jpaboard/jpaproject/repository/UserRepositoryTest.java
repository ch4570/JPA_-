package jpaboard.jpaproject.repository;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

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
    @DisplayName("없는 회원 조회 예외 테스트")
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

}