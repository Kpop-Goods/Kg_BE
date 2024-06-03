package hello.kpop.admin.controllers.agency;


import hello.kpop.admin.commons.ScriptExceptionProcess;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.admin.commons.menus.Menu;
import hello.kpop.admin.commons.menus.MenuDetail;
import hello.kpop.agency.dto.AgencyResponseDto;
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
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAgencyController implements ScriptExceptionProcess {

    private final AdminAgencyService agencyInfoService;
    private final HttpServletRequest request;



    @GetMapping("/agency")
    public String index(@ModelAttribute SearchData searchData, Model model){
        commonProcess("list",model);

        ListData<AgencyResponseDto> data = agencyInfoService.getList(searchData);
        model.addAttribute("agencies",data.getContent());
        model.addAttribute("pagination",data.getPagination());

        return "admin/agency/index";
    }


    private void commonProcess(String mode, Model model ){
        mode = Objects.requireNonNullElse(mode , "list");

        String pageTitle = "소속사";

        if(mode.equals("add")){
            pageTitle = "소속사 등록";
        } else if (mode.equals("update")) {
            pageTitle="소속사 수정";
        }

        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = mode.equals("update") ? "add" : subMenuCode;
        model.addAttribute("subMenuCode" , subMenuCode);

        List<MenuDetail> submenus = Menu.gets("agency");
        model.addAttribute("submenus", submenus);
        model.addAttribute("pageTitle",pageTitle);

        model.addAttribute("menuCode","agency");

    }

}
