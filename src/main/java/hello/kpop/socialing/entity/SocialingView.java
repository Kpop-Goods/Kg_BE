package hello.kpop.socialing.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "socialingview")
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
