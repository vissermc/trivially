package com.vissermc.trivially;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping({"/index", "/admin/vj82fba8ifi1yht45d1mnd3q0ihf8x"})
    public String getIndex() {
        return "/index.html";
    }
}

