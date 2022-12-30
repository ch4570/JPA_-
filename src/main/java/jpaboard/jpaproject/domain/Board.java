package jpaboard.jpaproject.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "ARTICLE_AUTHOR")
    private String author;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;

}
