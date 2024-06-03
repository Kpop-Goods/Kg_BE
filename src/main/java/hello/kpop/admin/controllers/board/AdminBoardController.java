package hello.kpop.admin.controllers.board;

import hello.kpop.admin.commons.ScriptExceptionProcess;
import hello.kpop.admin.commons.menus.Menu;
import hello.kpop.admin.commons.menus.MenuDetail;
import hello.kpop.board.dto.BoardData;
import hello.kpop.board.dto.BoardListData;
import hello.kpop.board.dto.BoardSearchData;
import hello.kpop.board.service.BoardInfoService;
import hello.kpop.board.service.BoardSaveService;
import hello.kpop.socialing.common.ListData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class AdminBoardController implements ScriptExceptionProcess {


    private final BoardInfoService boardInfoService;
    private final HttpServletRequest request;
    private final BoardSaveService saveService;


    @GetMapping
    public String index (@ModelAttribute BoardSearchData searchData, Model model){
        commonProcess( "list" , model );

        ListData<BoardListData> data  = boardInfoService.getList(searchData);
        model.addAttribute("boards" , data.getContent());
        model.addAttribute("pagination",data.getPagination());

        return "admin/board/index";
    }

    @GetMapping("/manage")
    public String list(@ModelAttribute BoardSearchData searchData , Model model){
        commonProcess("manage" , model);
        ListData<BoardListData> data = boardInfoService.getList(searchData);

        model.addAttribute("boards",data.getContent());
        model.addAttribute("pagination" , data.getPagination());

        return"admin/board/list";
    }




    @GetMapping("/add")
    public String register(@ModelAttribute BoardData from , Model model){
        commonProcess("add",model);

        return "admin/board/add";

    }

    @PostMapping("/save")
    public String save(@Valid BoardData form, Errors errors, Model model , Authentication authentication) {

        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        commonProcess(mode,  model);

        if (errors.hasErrors()) {
            return "admin/board/"+mode;
        }

        saveService.createBoard(form,errors,authentication);

        return "redirect:/admin/board";
    }
    private void commonProcess( String mode,Model model ){
        mode = Objects.requireNonNullElse(mode , "list");

        String pageTitle = "게시판";

        if(mode.equals("add")){
            pageTitle = "게시판 등록";
        } else if (mode.equals("update")) {
            pageTitle="게시판 수정";
        } else if (mode.equals("manage")) {
            pageTitle = "게시판 관리";
        }


        String subMenuCode = Menu.getSubMenuCode(request);
        subMenuCode = mode.equals("update") ? "add" : subMenuCode;
        model.addAttribute("subMenuCode" , subMenuCode);

        List<MenuDetail> submenus = Menu.gets("board");
        model.addAttribute("submenus", submenus);
        model.addAttribute("pageTitle",pageTitle);

        model.addAttribute("menuCode","board");

    }
}
