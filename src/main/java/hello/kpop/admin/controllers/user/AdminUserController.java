package hello.kpop.admin.controllers.user;

import hello.kpop.admin.commons.CommonProcess;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.admin.commons.menus.Menu;
import hello.kpop.admin.commons.menus.MenuDetail;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController implements CommonProcess {

    private final AdminUserService userInfoService;
    private final HttpServletRequest request;

    @GetMapping
    public String index(@ModelAttribute SearchData searchData, Model model){
        commonProcess("list" , model);

        ListData<UserResponseDto> data =userInfoService.getList(searchData);

        model.addAttribute("users", data.getContent());
        model.addAttribute("pagination",data.getPagination());

        return "/admin/user/index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirectURL",required = false) String redirectURL, Model model) {
        commonProcess(model, ProcessUtils.getMessage("로그인","common"));
        model.addAttribute("redirectURL", redirectURL);

        return "front/user/login";
    }


    private void commonProcess( String mode,Model model ){
        mode = Objects.requireNonNullElse(mode , "list");

        String pageTitle = "유저";

        if(mode.equals("add")){
            pageTitle = "유저 등록";
        } else if (model.equals("update")) {
            pageTitle="유저 수정";
        }


        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = mode.equals("update") ? "add" : subMenuCode;
        model.addAttribute("subMenuCode" , subMenuCode);

        List<MenuDetail> submenus = Menu.gets("user");
        model.addAttribute("submenus", submenus);
        model.addAttribute("pageTitle",pageTitle);

        model.addAttribute("menuCode","user");

    }
}
