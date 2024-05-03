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

    //이미지 url
    @Column(name = "image_url")
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
