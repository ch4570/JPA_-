package jpaboard.jpaproject.dto;

import jpaboard.jpaproject.domain.User;
import jpaboard.jpaproject.domain.UserRole;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto {

    private Long no;

    private String id;

    private String name;

    private String pwd;

    private String email;

    private UserRole userRole;
    private String regDate;
    private String modDate;

    public UserResponseDto(User user) {
        this.no = user.getNo();
        this.id = user.getId();
        this.name = user.getName();
        this.pwd = user.getPwd();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
        this.regDate = user.getRegDate();
        this.modDate = user.getModDate();
    }
}
