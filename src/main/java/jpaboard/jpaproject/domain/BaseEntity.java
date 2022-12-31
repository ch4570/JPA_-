package jpaboard.jpaproject.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {


    @Column(name = "REG_ID", length = 30)
    private String regId;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "MOD_ID", length = 30)
    private String modId;

    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
