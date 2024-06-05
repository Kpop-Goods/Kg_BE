package hello.kpop.admin.commons.menus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MenuDetail {

    private String code;
    private String name;
    private String url;

}
