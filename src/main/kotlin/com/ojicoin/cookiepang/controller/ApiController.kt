package com.ojicoin.cookiepang.controller

import com.ojicoin.cookiepang.service.CookieService
import com.ojicoin.cookiepang.service.InquiryService
import com.ojicoin.cookiepang.service.UserTagService
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.function.RequestPredicates.GET
import org.springframework.web.servlet.function.RequestPredicates.POST
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerResponse.created
import org.springframework.web.servlet.function.ServerResponse.ok
import org.springframework.web.servlet.function.body
import java.net.URI

@Controller
class ApiController(
    private val cookieService: CookieService,
    private val inquiryService: InquiryService,
    private val userTagService: UserTagService,
) {
    @Bean
    fun view() = route(GET("/users/{userId}/cookies/{cookieId}")) {
        val userId = it.pathVariable("userId").toLong()
        val cookieId = it.pathVariable("cookieId").toLong()
        val cookie = cookieService.view(userId = userId, cookieId = cookieId)
        ok().body(cookie) // TODO: cookieView 추가
    }

    @Bean
    fun create() = route(POST("/inquiries")) {
        // create inquiries
        val inquiryRequestDto = it.body<InquiryRequestDto>()

        inquiryService.create(inquiryRequestDto.title, inquiryRequestDto.senderUserId, inquiryRequestDto.receiverUserId)

        // TODO create certain uri path about created resource
        created(URI.create("")).build()
    }.andRoute(POST("/users/{userId}/tags")) {
        // create user interested tags
        val userId = it.pathVariable("userId").toLong()
        val userTagCreateDto = it.body<UserTagCreateDto>()

        userTagService.create(userId, userTagCreateDto.tagList)

        // TODO create certain uri path about created resource
        created(URI.create("")).build()
    }
}

data class InquiryRequestDto(
    val title: String,
    val senderUserId: Long,
    val receiverUserId: Long
)

data class UserTagCreateDto(
    val tagList: List<Long>
)
