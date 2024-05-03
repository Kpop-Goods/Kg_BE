package hello.kpop.admin.controllers;

import hello.kpop.user.dto.UserResponseDto;
import hello.kpop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminUserController")
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public String selectUser(@ModelAttribute Authentication authentication, Model model) throws Exception {

        UserResponseDto dto =  userService.searchUser(authentication);
        model.addAttribute("user", dto);
        return "/admin/user/index";
    }
}
