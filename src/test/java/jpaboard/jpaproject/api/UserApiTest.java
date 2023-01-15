package jpaboard.jpaproject.api;

import jpaboard.jpaproject.dto.UserRequestDto;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserApiTest {

    @Autowired private Validator validator;

    @Test
    @DisplayName("필수항목 유효성 검사 테스트")
    public void validateUserIsRequired() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .name("RexSeo")
                .build();

        // when
        Set<ConstraintViolation<UserRequestDto>> validate = validator.validate(user);

        // then
        Iterator<ConstraintViolation<UserRequestDto>> iterator = validate.iterator();
        List<String> messageList = new ArrayList<>();

        while(iterator.hasNext()) {
            String message = iterator.next().getMessage();
            messageList.add(message);
            System.out.println("message = " + message);
        }

        assertThat(messageList).contains("회원 이름은 필수 입력 항목입니다.",
                "회원 ID는 필수 입력 항목입니다.","비밀번호는 필수 입력 항목입니다.",
                "이메일은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("비밀번호 & 이메일 형식 유효성 검사 테스트")
    public void validateUserIsHasPattern() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .id("Java")
                .name("Dev")
                .pwd("cadksfj")
                .email("aaa@aaa.com")
                .build();

        // when
        Set<ConstraintViolation<UserRequestDto>> validate = validator.validate(user);

        // then
        Iterator<ConstraintViolation<UserRequestDto>> iterator = validate.iterator();
        List<String> messageList = new ArrayList<>();

        while(iterator.hasNext()) {
            String message = iterator.next().getMessage();
            messageList.add(message);
            System.out.println("message = " + message);
        }

        assertThat(messageList).contains("이메일 형식에 맞게 입력해주세요.", "비밀번호는 영문과 특수문자를 포함하며 8자 이상, 20자 이하여야 합니다.");
    }

    @Test
    @DisplayName("회원 길이제한 유효성 검사 테스트")
    public void validateUserLengthTest() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .id("aaaaaaaaaaa")
                .name("Devaklsdfjlkdsajflsdfgsdfgfkdjsglfsdkjgklsgsgdf")
                .pwd("fdkjlk1232!")
                .email("ccc@ccc.com")
                .build();

        // when
        Set<ConstraintViolation<UserRequestDto>> validate = validator.validate(user);

        // then
        Iterator<ConstraintViolation<UserRequestDto>> iterator = validate.iterator();
        List<String> messageList = new ArrayList<>();

        while(iterator.hasNext()) {
            String message = iterator.next().getMessage();
            messageList.add(message);
            System.out.println("message = " + message);
        }

        assertThat(messageList).contains("회원 ID는 최대 30자까지 입력 가능합니다.", "회원 이름은 최대 20자까지 입력 가능합니다.");
    }
}
