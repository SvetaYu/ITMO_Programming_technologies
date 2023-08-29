package org.tech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}
