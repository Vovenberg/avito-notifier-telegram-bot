package com.killprojects.avitonotifier.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class BotController : TelegramLongPollingBot() {
    private val logger: Logger = Logger.getLogger("[AvitoBot]")

    @Value("\${telegram.token}")
    lateinit var token: String
    @Value("\${telegram.commands.start}")
    lateinit var startCommand: String
    @Value("\${telegram.commands.echo}")
    lateinit var echoCommand: String

    /**
     * Return bot username of this bot
     */
    override fun getBotUsername(): String = "AdsNotifier"

    /**
     * Returns the token of the bot to be able to perform Telegram Api Requests
     * @return Token of the bot
     */
    override fun getBotToken(): String = token

    /**
     * This method is called when receiving updates via GetUpdates method
     * @param update Update received
     */
    override fun onUpdateReceived(update: org.telegram.telegrambots.meta.api.objects.Update?) {
        logger.log(Level.INFO, "Got update: $update")

        val text = update?.message?.text ?: ""
        val chatId = update?.message?.chat?.id ?: throw IllegalStateException("ChatId required not null")
        when {
            text.startsWith(startCommand) -> onStartCommand(chatId)
            text.startsWith(echoCommand) -> onEchoCommand(chatId, text)
        }
    }

    private fun onStartCommand(chatId: Long) = sendMessage(chatId, "Hello! I'm AdsNotifierBot.")

    private fun onEchoCommand(chatId: Long, text: String) = sendMessage(chatId, text.trim())

    private fun sendMessage(chatId: Long, text: String) {
        val message: SendMessage = SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(text)
        try {
            execute(message) // Call method to send the message
        } catch (e: TelegramApiException) {
            logger.warning("Error for $chatId with text $text: $e")
        }
    }
}