package hello.kpop.socialing.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SocialingViewId.class)
public class SocialingView {

    @Id
    private Long id;// 소셜링 아이디

    @Id
    @Column(name = "_uid")
    private int uId;
}
