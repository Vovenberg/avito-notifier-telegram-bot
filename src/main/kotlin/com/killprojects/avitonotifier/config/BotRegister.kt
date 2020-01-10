package com.killprojects.avitonotifier.config

import com.killprojects.avitonotifier.controller.BotController
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

@Component
class BotRegister(var botController: BotController) : ApplicationListener<ApplicationReadyEvent> {

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        ApiContextInitializer.init()
        val telegramBotsApi = TelegramBotsApi()
        try {
            telegramBotsApi.registerBot(botController)
        } catch (e: TelegramApiRequestException) {
            e.printStackTrace()
        }
    }
}