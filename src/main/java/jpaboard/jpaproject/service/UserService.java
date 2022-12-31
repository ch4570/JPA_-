package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import jpaboard.jpaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*
    *   회원 가입
    *   @param User
    *   @return userNo
    * */
    @Transactional
    public User join(User user) {
        isDuplicate(user);
        user.setUserRole(UserRole.USER);
        return userRepository.save(user);
    }

    /*
    *   회원 한명 조회
    *   @param userNo
    *   @return User
    * */
    public User findOneUser(Long userNo) {
        Optional<User> findUser = userRepository.findById(userNo);
        if (findUser.isEmpty()) {
            throw new IllegalStateException("조회하려는 회원은 존재하지 않습니다.");
        } else {
            return findUser.get();
        }
    }

    /*
    *   회원 한명 이름으로 조회
    *   @param userNo
    *   @return User
    * */
    public User findOneUserByName(String name) {
        return userRepository.findByName(name);
    }

    /*
    *   회원 한명 업데이트
    *   @param User
    *   @return userNo
    * */
    @Transactional
    public User modifyUser(User user) {
        User modifyUser = userRepository.findById(user.getNo()).get();
        modifyUser.setEmail(user.getEmail());
        modifyUser.setName(user.getName());
        modifyUser.setPwd(user.getPwd());
        modifyUser.setModId(user.getModId());
        return modifyUser;
    }

    /*
    *   중복 회원 조회 메서드
    *   @param User
    *   @return boolean
    * */
    private boolean isDuplicate(User user) {
        User findUser = userRepository.findByName(user.getName());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } else {
            return true;
        }

    }
}
