package jpaboard.jpaproject.repository;

import jpaboard.jpaproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
    *   회원 가입 & 회원 수정
    *   @param User
    *   @return userNo
    * */
    public User save(User user);

    /*
    *   회원 한명 조회
    *   @param userNo
    *   @return User
    * */
    public Optional<User> findById(Long id);

    /*
    *   회원 이름으로 조회
    *   @param userName
    *   @return User
    * */
    public User findByName(String name);

    /*
    *   회원 한명 삭제
    *   @param userNo
    * */
    public void delete(User user);
}
