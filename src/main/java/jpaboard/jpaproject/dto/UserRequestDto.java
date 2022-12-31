package jpaboard.jpaproject.dto;

import jpaboard.jpaproject.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class UserRequestDto {

    private Long no;

    @NotEmpty(message = "회원 ID는 필수 입력 항목입니다.")
    @Length(max = 30, message = "회원 ID는 최대 30자까지 입력 가능합니다.")
    private String id;

    @NotEmpty(message = "회원 이름은 필수 입력 항목입니다.")
    @Length(max = 20, message = "회원 이름은 최대 20자까지 입력 가능합니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 영문과 특수문자를 포함하며 8자 이상이어야 합니다.")
    private String pwd;

    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    public User userRequestToEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .pwd(pwd)
                .email(email)
                .build();
    }
}
