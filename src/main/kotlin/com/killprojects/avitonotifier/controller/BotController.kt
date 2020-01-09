package com.killprojects.avitonotifier.controller

import com.killprojects.avitonotifier.client.TelegramApiClient
import com.killprojects.avitonotifier.model.Update
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class BotController(val telegramApiClient: TelegramApiClient) {
    private val logger: Logger = Logger.getLogger("[AvitoBot]")

    @Value("\${telegram.commands.start}")
    lateinit var startCommand: String
    @Value("\${telegram.commands.echo}")
    lateinit var echoCommand: String

    @PostMapping("/\${token}")
    fun onUpdate(@RequestBody update: Update) {
        logger.log(Level.INFO, "Got update: " + update)
        if (update.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text
            when {
                text?.startsWith(startCommand) == true -> onStartCommand(chatId)
                text?.startsWith(echoCommand) == true -> onEchoCommand(chatId, text)
            }
        }
    }

    private fun onStartCommand(chatId: Long) = sendMessage(chatId, "Hello! I'm AvitoNotifierBot.")

    private fun onEchoCommand(chatId: Long, text: String) = sendMessage(chatId, text.trim())

    private fun sendMessage(chatId: Long, text: String) = telegramApiClient.sendMessage(chatId.toString(), text)
}