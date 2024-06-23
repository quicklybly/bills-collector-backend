package com.quicklybly.billscollector.controller

import com.quicklybly.billscollector.service.AuthenticationService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
@RequestMapping("/hello")
@RestController
class HelloController(
    private val service: AuthenticationService
) {

    @GetMapping
    fun hello(): String {
        val client = service.extractClient()
        return "${client.username}, hello from bills collector!"
    }
}
