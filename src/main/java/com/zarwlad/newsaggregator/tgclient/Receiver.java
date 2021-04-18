package com.zarwlad.newsaggregator.tgclient;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@RequiredArgsConstructor
@Setter
@Getter
public class Receiver extends TelegramLongPollingBot {
    @Value("${application.telegram.username}")
    private String botUsername;

    @Value("${application.telegram.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            log.info("getMessage: " + update.getMessage().toString());
        }
      if (update.hasChannelPost()) {
          log.info("getChannelPost: " + update.getChannelPost().toString());
      }
        SendMessage message = new SendMessage();

        Chat chat = new Chat();
        chat.setInviteLink("https://t.me/iporobinhood");
    }
}
