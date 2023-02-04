package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import jpaboard.jpaproject.dto.UserLoginRequestDto;
import jpaboard.jpaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    *   회원 가입
    *   @param User
    *   @return userNo
    * */
    @Transactional
    public User join(User user) {
        isDuplicate(user);
        // 비밀번호 암호화 후 저장
        user.setPwd(passwordEncoder.encode("{bcrypt}"+user.getPwd()));
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
    *   회원 한명 아이디로 조회
    *   @param UserLoginRequestDto
    *   @return User
    * */
    public User findOneUserById(String id) {
        return userRepository.findById(id);
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
    *   회원 전체 삭제 - Test Code 전용
    * */
    @Transactional
    public void removeAllUsers() {
        userRepository.deleteAll();
    }

    /*
    *   회원 전체 조회
    *   @param Sort
    *   @return User<List>
    * */
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "no"));
        if(userList.isEmpty()) {
            throw new IllegalStateException("회원 가입된 회원이 한명도 없습니다.");
        }
        return userList;
    }

    /*
    *   회원 로그인 정보 일치여부 검사
    *   @param UserLoginRequestDto
    *   @return boolean
    * */
    public boolean isAuthorization(UserLoginRequestDto dto) {

        // 입력한 회원 ID로 회원 한명을 조회한다.
        User user = findOneUserById(dto.getId());

        // 비밀번호에 암호화 방식을 붙여준다.
        String password = "{bcrypt}"+dto.getPwd();

        // 조회한 회원객체가 null 이면 없는 회원이므로 false 반환
        if(user == null) {
            return false;
        }

        // 조회한 회원 객체가 null이 아니라면 비밀번호가 일치하는지 조회
        if(passwordEncoder.matches(password, user.getPwd())) {
            return true;
        } else {
            return false;
        }

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
