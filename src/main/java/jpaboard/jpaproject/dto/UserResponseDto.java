package jpaboard.jpaproject.dto;

import jpaboard.jpaproject.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor @ToString
public class UserResponseDto {


    private Long no;

    private String id;

    private String name;

    private String pwd;

    private String email;

    private UserRole userRole;
}
