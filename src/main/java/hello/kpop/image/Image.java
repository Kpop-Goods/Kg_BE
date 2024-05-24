package hello.kpop.image;

import hello.kpop.image.dto.ImageDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long ImageId;

    // 이미지 url
    @Column(name = "image_url")
    private String imageUrl;

    // 엔티티 분류 코드
    @Column(name = "entity_type")
    private Integer entityType;

    // 엔티티 ID
    @Column(name = "entity_id")
    private Long entityId;


    public Image(String imageUrl, Integer entityType, Long entityId) {
        this.imageUrl = imageUrl;
        this.entityType = entityType;
        this.entityId = entityId;
    }


}
