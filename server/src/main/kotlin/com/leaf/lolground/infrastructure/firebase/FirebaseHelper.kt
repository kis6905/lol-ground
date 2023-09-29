package com.leaf.lolground.infrastructure.firebase

import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FirebaseHelper {

    @Value("\${firebase.cloud-messaging.credential}")
    lateinit var fcmCredentialJson: String

    fun getAccessToken(): String? {
        val googleCredentials = GoogleCredentials
            .fromStream(fcmCredentialJson.byteInputStream())
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging", "https://www.googleapis.com/auth/cloud-platform"))
        return googleCredentials.refreshAccessToken()?.tokenValue
    }

}
