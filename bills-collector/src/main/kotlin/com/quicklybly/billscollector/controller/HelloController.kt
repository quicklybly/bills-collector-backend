package com.quicklybly.billscollector.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
@RequestMapping("/hello")
@RestController
class HelloController {

    @GetMapping
    fun hello(): String {
        return "${(SecurityContextHolder.getContext().authentication.principal as Jwt).claims["preferred_username"]}, hello from bills collector!"
    }
}
