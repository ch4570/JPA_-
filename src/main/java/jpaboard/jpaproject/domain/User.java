package jpaboard.jpaproject.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "USER_NO")
    private Long no;

    @Column(name = "USER_ID", unique = true, length = 30)
    private String id;

    @Column(name = "USER_NAME", length = 20)
    private String name;

    @Column(name = "USER_PWD", length = 100)
    private String pwd;

    @Column(name = "USER_EMAIL", length = 100)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(no, user.no) && Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) && Objects.equals(pwd, user.pwd) &&
                Objects.equals(email, user.email) && Objects.equals(boardList, user.boardList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, id, name, pwd, email, boardList);
    }
}
