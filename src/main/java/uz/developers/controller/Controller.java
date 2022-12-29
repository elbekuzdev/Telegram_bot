package uz.developers.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import uz.developers.model.Request;

import java.util.List;

import static uz.developers.service.AdminService.*;

public class Controller {
    public static void admin_request(List<Request> updates, TelegramBot bot) {
        for (Request update : updates) {
            Long userId = update.getUpdate().message().from().id();
            if (update.getUpdate().message().text().equals("/start")) {
                saveDb(update);
                bot.execute(new SendMessage(userId, "Salom, admin"));
            } else if (update.getUpdate().message().text().equals("/users")) {
                sendUser(userId, bot);
            } else if (update.getUpdate().message().text().equals("/send")) {
                sendAdvertisement(update, bot);
            } else if (update.getUpdate().message().text().equals("/send_forward")) {
                sendAdvertisementForward(update, bot);
            }

        }
    }

    public static void user_request(List<Request> updates, TelegramBot bot) {
        for (Request update : updates) {
            Long userId = update.getUpdate().message().from().id();
            if (update.getUpdate().message().text().equals("/start")) {
                saveDb(update);
            }
            bot.execute(new SendMessage(userId, "Salom, foydalanuvchi"));
        }
    }

}
