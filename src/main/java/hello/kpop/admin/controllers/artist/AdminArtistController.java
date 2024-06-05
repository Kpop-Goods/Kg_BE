package hello.kpop.admin.controllers.artist;

import hello.kpop.admin.commons.ScriptExceptionProcess;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.admin.commons.menus.Menu;
import hello.kpop.admin.commons.menus.MenuDetail;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.socialing.common.ListData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/artist")
@RequiredArgsConstructor
public class AdminArtistController implements ScriptExceptionProcess {

    private final AdminArtistService adminArtistService;
    private final HttpServletRequest request;


    @GetMapping
    public String index(@ModelAttribute SearchData searchData, Model model){
        commonProcess("list" , model);

        ListData<ArtistResponseDto> data =adminArtistService.getList(searchData);

        model.addAttribute("artists", data.getContent());
        model.addAttribute("pagination",data.getPagination());

        return "/admin/artist/index";
    }



    private void commonProcess( String mode,Model model ){
        mode = Objects.requireNonNullElse(mode , "list");

        String pageTitle = "아티스트";

        if(mode.equals("add")){
            pageTitle = "아티스트 등록";
        } else if (mode.equals("update")) {
            pageTitle="아티스트 수정";
        }


        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = mode.equals("update") ? "add" : subMenuCode;
        model.addAttribute("subMenuCode" , subMenuCode);

        List<MenuDetail> submenus = Menu.gets("artist");
        model.addAttribute("submenus", submenus);
        model.addAttribute("pageTitle",pageTitle);

        model.addAttribute("menuCode","artist");

    }

}
