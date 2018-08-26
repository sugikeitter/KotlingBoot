package com.sugikeitter.kotlingboot.controller

import com.sugikeitter.kotlingboot.data.Hello
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hell(): Hello {
        return Hello()
    }
}