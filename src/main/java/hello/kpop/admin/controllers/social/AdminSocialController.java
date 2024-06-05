package hello.kpop.admin.controllers.social;


import hello.kpop.admin.commons.ScriptExceptionProcess;
import hello.kpop.admin.commons.menus.Menu;
import hello.kpop.admin.commons.menus.MenuDetail;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.dto.SocialingData;
import hello.kpop.socialing.dto.SocialingListData;
import hello.kpop.socialing.dto.SocialingSearchData;
import hello.kpop.socialing.service.SocialingInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller("adminSocialController")
@RequiredArgsConstructor
@RequestMapping("/admin/social")
public class AdminSocialController implements ScriptExceptionProcess {


    private final SocialingInfoService socialingInfoService;
    private final HttpServletRequest request;
    private final AdminSocialService adminSocialService;


    @GetMapping
    public String index(@ModelAttribute SocialingSearchData searchData, Model model){
        commonProcess("list",model);

        ListData<SocialingListData> data = socialingInfoService.getList(searchData);

        model.addAttribute("social", data.getContent());
        model.addAttribute("pagination",data.getPagination());


        return "/admin/social/index";
    }


    @GetMapping("/add")
    public String register(@ModelAttribute SocialingData from , Model model){
        commonProcess(  "add",model);
        List<String> list = adminSocialService.indexName();
        model.addAttribute("nameList",list);

        return "admin/social/add";
    }

    @PostMapping("/save")
    public String save(@Valid SocialingData form, Errors errors, Model model ) {

        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        commonProcess(mode,  model);

        if (errors.hasErrors()) {

            List<String> list = adminSocialService.indexName();
            model.addAttribute("nameList",list);


            return "admin/social/"+mode;

        }


        return "redirect:/admin/social";
    }


    private void commonProcess(String mode,Model model){
        mode = Objects.requireNonNullElse(mode,"list");

        String pageTitle = "소셜 목록";

        if(mode.equals("add")){
            pageTitle="소셜 등록";
        } else if (mode.equals("update")) {
            pageTitle="소셜 수정";
        }

        model.addAttribute("menuCode","social");
        String submenuCode = Menu.getSubMenuCode(request);
        submenuCode = mode.equals("update") ? "add":submenuCode;
        model.addAttribute("subMenuCode",submenuCode);

        List<MenuDetail> submenus =Menu.gets("social");
        model.addAttribute("submenus",submenus);

        model.addAttribute("pageTitle" , pageTitle);


    }




}
