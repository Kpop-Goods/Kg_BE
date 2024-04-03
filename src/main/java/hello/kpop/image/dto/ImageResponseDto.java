package hello.kpop.image.dto;

import hello.kpop.image.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponseDto {

    private Long ImageId;
    private String imageUrl;

    public ImageResponseDto(Image entity) {
        this.ImageId = entity.getImageId();
        this.imageUrl = entity.getImageUrl();
    }
}
