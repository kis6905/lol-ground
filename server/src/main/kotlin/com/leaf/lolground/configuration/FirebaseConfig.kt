package com.leaf.lolground.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FirebaseConfig {

    @Value("\${firebase.cloud-messaging.credential}")
    lateinit var fcmCredentialJson: String

    @Bean
    fun googleCredentials(): GoogleCredentials {
        return GoogleCredentials
            .fromStream(fcmCredentialJson.byteInputStream())
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging", "https://www.googleapis.com/auth/cloud-platform"))
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val options = FirebaseOptions.builder()
            .setCredentials(googleCredentials())
            .build()
        val app = FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance(app)
    }
}
