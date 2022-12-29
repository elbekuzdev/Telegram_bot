package uz.developers.controller;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import uz.developers.model.Request;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static uz.developers.TelegramBotRunner.bot;
import static uz.developers.configuration.Properties.ADMINS;
import static uz.developers.configuration.Properties.NOTIFY;

public class InternalBot {
    public static int listener(List<Update> updates) {
        List<Request> requests = new LinkedList<>();
        for (Update update: updates){
            requests.add(new Request(update));
        }
        for (Request update : requests) {
            if (isAdmin(update.getUpdate().message().from().id())){
                Controller.admin_request(requests, bot);
            }else{
                Controller.user_request(requests, bot);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public static void notify_admins() {
        for (Long adminId : ADMINS) {
            SendMessage request = new SendMessage(adminId, NOTIFY).parseMode(ParseMode.HTML).disableWebPagePreview(true).disableNotification(true).replyMarkup(new ForceReply());

            bot.execute(request, new Callback<SendMessage, SendResponse>() {
                @Override
                public void onResponse(SendMessage request, SendResponse response) {
                    System.out.println(request);
                }

                @Override
                public void onFailure(SendMessage request, IOException e) {
                    System.out.println(request);
                }
            });
        }
    }

    private static boolean isAdmin(Long adminId){
        for (Long id: ADMINS)
            if (adminId.equals(id))
                return true;
        return false;
    }

}
