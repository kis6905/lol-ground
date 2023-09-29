package com.leaf.lolground.application.api.fcm

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.infrastructure.firebase.FirebaseHelper
import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@ApiController
class FcmController(
    val firebaseHelper: FirebaseHelper,
) {

    // TODO: 이걸 계속 남겨? 말어?
    @GetMapping("/fcm/accessToken")
    fun getAccessToken(): ResponseEntity<String?> {
        return try {
            val token = firebaseHelper.getAccessToken() ?: "null"
            ResponseEntity.ok(token)
        } catch (e: Exception) {
            logger.warn("", e)
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build()
        }
    }

}
