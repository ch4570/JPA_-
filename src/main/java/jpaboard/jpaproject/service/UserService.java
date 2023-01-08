package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import jpaboard.jpaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        findUser.orElseThrow(() -> new IllegalStateException("조회하려는 회원은 존재하지 않습니다."));
        return findUser.get();
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
        User modifyUser = userRepository.save(user);
        return modifyUser;
    }

    /*
    *   회원 한명 삭제
    *   @param userNo
    * */
    @Transactional
    public void removeUser(Long userNo) {
        User user = userRepository.findById(userNo)
                        .orElseThrow(() ->  new IllegalStateException("해당 회원번호와 일치하는 회원이 없어 삭제가 불가능합니다."));
        userRepository.delete(user);
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
