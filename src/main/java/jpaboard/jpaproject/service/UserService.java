package jpaboard.jpaproject.service;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        Long userNo = userRepository.insertUser(user);
        return userNo;
    }

}
