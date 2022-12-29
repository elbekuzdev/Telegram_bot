package uz.developers;

import com.pengrad.telegrambot.TelegramBot;
import uz.developers.controller.InternalBot;

import static java.lang.Thread.sleep;
import static uz.developers.controller.InternalBot.notify_admins;
import static uz.developers.configuration.Properties.BOT_TOKEN;

public class TelegramBotRunner {
    public static final TelegramBot bot = new TelegramBot(BOT_TOKEN);


    public static void main(String[] args) {
        notify_admins();
        bot.setUpdatesListener(InternalBot::listener);



    }





}
