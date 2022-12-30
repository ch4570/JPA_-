package jpaboard.jpaproject.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "USER_NO")
    private Long no;

    @Column(name = "USER_ID", unique = true)
    private String id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_PWD")
    private String pwd;

    @Column(name = "USER_EMAIL")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();
}
