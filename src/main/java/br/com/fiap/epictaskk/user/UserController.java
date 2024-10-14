package br.com.fiap.epictaskk.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String index(Model model, @AuthenticationPrincipal DefaultOAuth2User principal){
        var user = (User) principal;
        model.addAttribute("user", user);
        model.addAttribute("avatar", user.avatar);
        model.addAttribute("users", userService.getRanking());
        return "users";
    }

}
