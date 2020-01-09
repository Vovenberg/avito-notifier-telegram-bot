package com.killprojects.avitonotifier.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.killprojects.avitonotifier.client.TelegramApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class AppConfiguration

@Bean
fun mapper() = ObjectMapper().apply {
    registerModule(KotlinModule())
}

@Bean
fun telegram(
        @Value("\${telegram.base-endpoint}") telegramBaseUrl: String,
        @Value("\${telegram.token}") token: String
): TelegramApiClient {
    val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("${telegramBaseUrl}${token}/")
            .build()

    return retrofit.create(TelegramApiClient::class.java);
}