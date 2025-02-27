package com.ojicoin.cookiepang.service

import com.ojicoin.cookiepang.config.ContractProperties
import com.ojicoin.cookiepang.controller.Action
import com.ojicoin.cookiepang.controller.CookieHistory
import com.ojicoin.cookiepang.controller.CookieView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class ViewAssembler(
    @Autowired val cookieService: CookieService,
    @Autowired val userService: UserService,
    @Autowired val viewCountService: ViewCountService,
    @Autowired val contractProperties: ContractProperties,
) {

    fun cookieView(viewerId: Long, cookieId: Long): CookieView {
        val cookie = cookieService.get(cookieId)
        val creator = userService.getById(cookie.authorUserId)
        val owner = userService.getById(cookie.ownedUserId)
        val viewer = userService.getById(viewerId)
        val answer: String? = if (viewerId != owner.id) {
            null
        } else {
            cookie.content
        }

        viewer.view(cookie)
        cookieService.publishEvent(cookie)

        val viewCount = viewCountService.getAllViewCountsByCookieId(cookieId)

        // TODO: 블록체인 네트워크에서 히스토리 조회후 변환 로직 추가
        return CookieView(
            question = cookie.title,
            answer = answer,
            collectorName = owner.nickname,
            creatorName = creator.nickname,
            contractAddress = contractProperties.address,
            nftTokenId = cookie.nftTokenId,
            viewCount = viewCount,
            price = cookie.price,
            histories = listOf(
                CookieHistory(
                    action = Action.CREATE,
                    content = """
                            '상일동 치타'님이 'Q.내가 여자친구가 있을까'를 망치 34개로 만들었습니다.
                    """.trimIndent(),
                    createdAt = Instant.now(),
                ),
                CookieHistory(
                    action = Action.BUY,
                    content = """
                            '강동구 호랑이'님이 'Q.내가 여자친구가 있을까'를 망치 34개로 깠습니다.
                    """.trimIndent(),
                    createdAt = Instant.now(),
                ),
                CookieHistory(
                    action = Action.MODIFY,
                    content = """
                            '강동구 호랑이'님이 'Q.내가 여자친구가 있을까'를 망치 32개로 수정했습니다.
                    """.trimIndent(),
                    createdAt = Instant.now()
                )
            )
        )
    }
}
