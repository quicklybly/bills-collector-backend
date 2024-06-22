package com.quicklybly.billscollector.controller

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
@RequestMapping("/hello")
@RestController
class HelloController {

    @GetMapping
    fun hello() = "hello from bills collector!"
}
