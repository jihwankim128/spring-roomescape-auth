package roomescape.controller.client.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.global.auth.PreAuth;

@Controller
public class PageController {

    @PreAuth
    @GetMapping("/reserve")
    public String reserve() {
        return "forward:/reservation.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "forward:/admin.html";
    }

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/signup")
    public String signup() {
        return "forward:/signup.html";
    }

    @PreAuth
    @GetMapping("/search")
    public String search() {
        return "forward:/search.html";
    }
}
