package com.vissermc.trivially;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/index")
    public String getIndex()
    {
        return "index.html";
    }
}
