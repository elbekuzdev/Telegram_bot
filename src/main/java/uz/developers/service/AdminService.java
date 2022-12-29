package uz.developers.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import uz.developers.dao.UserDao;
import uz.developers.model.Request;

import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class AdminService {

    public static void saveDb(Request update) {
        UserDao userDao = UserDao.getUserDao();
        userDao.save(update.getUpdate().message().from().id(), update.getUpdate().message().from().firstName(), update.getUpdate().message().from().lastName(), update.getUpdate().message().from().username());
    }

    public static void sendUser(Long userId, TelegramBot bot) {
        UserDao userDao = UserDao.getUserDao();
        List<HashMap<String, Object>> users = userDao.getAll();
        for (HashMap<String, Object> user : users) {
            Object id = user.get("id");
            Object firstname = user.get("firstname");
            Object lastname = user.get("lastname");
            Object username = user.get("username");
            String data = "\uD83D\uDCF2 Id: " + id + "\n" + "\uD83D\uDDE3 Foydalanuvchi: " + firstname + " " + (lastname == null ? " " : lastname) + "\n" + "\uD83D\uDCBE Username: " + (username == null ? "O'rnatmagan" : ("@" + username));
            bot.execute(new SendMessage(userId, data));

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendAdvertisementForward(Request update, TelegramBot bot) {
        UserDao userDao = UserDao.getUserDao();
        List<HashMap<String, Object>> users = userDao.getAll();
        if (update.getUpdate().message().replyToMessage() != null) {
            Long fromChatId = update.getUpdate().message().chat().id();
            Integer messageId = update.getUpdate().message().replyToMessage().messageId();
            for (HashMap<String, Object> user : users) {
                bot.execute(new ForwardMessage(user.get("id"), fromChatId, messageId));
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            bot.execute(new SendMessage(update.getUpdate().message().from().id(), "Reply qilinmaganku").replyToMessageId(update.getUpdate().message().messageId()));
        }
    }

    public static void sendAdvertisement(Request update, TelegramBot bot) {
        UserDao userDao = UserDao.getUserDao();
        List<HashMap<String, Object>> users = userDao.getAll();
        if (update.getUpdate().message().replyToMessage() != null) {
            String text = update.getUpdate().message().replyToMessage().text();
            for (HashMap<String, Object> user : users) {
                bot.execute(new SendMessage(user.get("id"), text));
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            bot.execute(new SendMessage(update.getUpdate().message().from().id(), "Reply qilinmaganku").replyToMessageId(update.getUpdate().message().messageId()));
        }

    }
}
