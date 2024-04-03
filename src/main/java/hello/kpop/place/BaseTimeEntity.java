package hello.kpop.place;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    //등록일
    @Column(name = "reg_dt")
    @CreatedDate
    private LocalDateTime regDt;

    //수정일
    @Column(name = "mod_dt")
    @LastModifiedDate
    private LocalDateTime modDt;
}
