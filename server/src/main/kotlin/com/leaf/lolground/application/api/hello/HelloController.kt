package com.leaf.lolground.application.api.hello

import com.leaf.lolground.application.annotations.ApiController
import org.springframework.web.bind.annotation.GetMapping

@ApiController
class HelloController {
    @GetMapping("/hello")
    fun hello(): String {
        return "ok"
    }
}