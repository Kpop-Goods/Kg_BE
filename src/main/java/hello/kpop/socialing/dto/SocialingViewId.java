package hello.kpop.socialing.dto;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SocialingViewId implements Serializable {

    private Long id;
    private int uId;


}
