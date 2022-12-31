package jpaboard.jpaproject.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ARTICLE_NO")
    private Long id;

    @Column(name = "ARTICLE_TITLE", length = 100)
    private String title;

    @Column(name = "ARTICLE_CONTENT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "ARTICLE_COUNT")
    private int count;

    @Column(name = "ARTICLE_AUTHOR", length = 30)
    private String author;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;


    // == 연관관계 편의 메서드 == //
    public void addUser(User user) {
        this.user = user;
        user.getBoardList().add(this);
    }
}
