package com.leaf.lolground.infrastructure.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Component

@Component
class FirebaseHelper(
    val googleCredentials: GoogleCredentials,
    val firebaseMessaging: FirebaseMessaging,
) {

    fun getAccessToken(): String? {
        return googleCredentials.refreshAccessToken()?.tokenValue
    }

    // TODO: test
    fun sendMessage(fcmToken: String, title: String, content: String) {
        val message: Message = Message.builder()
            .putData("title", title)
            .putData("content", content)
            .setToken(fcmToken)
            .build()
        firebaseMessaging.send(message)
    }

}
