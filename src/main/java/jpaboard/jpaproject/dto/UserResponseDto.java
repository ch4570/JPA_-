package jpaboard.jpaproject.dto;

import jpaboard.jpaproject.domain.UserRole;
import lombok.*;

@Builder
@AllArgsConstructor
public class UserResponseDto {


    private Long no;

    private String id;

    private String name;

    private String pwd;

    private String email;

    private UserRole userRole;

    public UserResponseDto() {

    }
}
