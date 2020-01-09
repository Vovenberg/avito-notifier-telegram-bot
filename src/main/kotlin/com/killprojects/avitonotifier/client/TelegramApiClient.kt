package com.killprojects.avitonotifier.client

import retrofit2.http.Field
import retrofit2.http.POST

interface TelegramApiClient {
    @POST("/sendMessage")
    fun sendMessage(@Field("chat_id") chatId: String, @Field("text") text: String): Void
}