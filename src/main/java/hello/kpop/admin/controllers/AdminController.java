package hello.kpop.admin.controllers;

import hello.kpop.board.dto.BoardListData;
import hello.kpop.board.dto.BoardSearchData;
import hello.kpop.board.service.BoardInfoService;
import hello.kpop.socialing.common.ListData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BoardInfoService infoService;



    @GetMapping("/home")
    public String index(@ModelAttribute BoardSearchData form , Model model) {

        ListData<BoardListData> data = infoService.getList(form);

        model.addAttribute("items",data.getContent());
        model.addAttribute("pagination",data.getPagination());


        return "admin/main/index";
    }




}
