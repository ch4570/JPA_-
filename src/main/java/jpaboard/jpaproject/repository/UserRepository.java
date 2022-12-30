package jpaboard.jpaproject.repository;

import jpaboard.jpaproject.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /*
    *   회원 가입
    *   @param User
    *   @return userNo
    * */
    public Long insertUser(User user) {
        em.persist(user);
        return user.getNo();
    }

    /*
    *   회원 한명 조회
    *   @param userNo
    *   @return User
    * */
    public User findOneUser(Long userNo) {
        return em.find(User.class, userNo);
    }
}
