package com.ojicoin.cookiepang.dto

import com.ojicoin.cookiepang.domain.AskStatus
import com.ojicoin.cookiepang.domain.Cookie
import com.ojicoin.cookiepang.domain.CookieStatus
import javax.validation.constraints.Size

data class CreateCookie(
    val question: String,
    val answer: String,
    val price: Long,
    val authorUserId: Long,
    val ownedUserId: Long,
    val txHash: String,
    val categoryId: Long,
)

data class ViewCategory(
    val categoryId: Long,
    val name: String,
    val color: String,
)

data class UpdateCookie(
    val price: Long?,
    val status: CookieStatus?,
    val purchaserUserId: Long?,
)

data class CreateAsk(
    val title: String,
    val senderUserId: Long,
    val receiverUserId: Long,
)

data class CreateUser(
    val walletAddress: String,

    @field:Size(max = 100)
    val nickname: String,
    val introduction: String?,
    val profileUrl: String?,
    val backgroundUrl: String?,
)

data class UpdateUser(
    val introduction: String?
)

data class UpdateAsk(
    val title: String?,
    val status: AskStatus?,
)

data class GetCookiesResponse(
    val totalCount: Int,
    val page: Int,
    val size: Int,
    val cookies: List<Cookie>,
)
