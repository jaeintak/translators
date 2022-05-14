package com.translator.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/index")
    fun index():String {
        return "index"
    }
}