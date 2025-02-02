package bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class Bot extends TelegramLongPollingBot {

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      long chatId = message.getChatId(); // ID чата с пользователем
      String text = message.getText();   // Текст сообщения
      if ("/start".equals(text)) {
        sendStartReply(chatId);
        return;
      }

      // Пересылка сообщения в твой личный профиль
      forwardMessageToAdmin(chatId, text);

      // Ответ пользователю
      sendStandardReply(chatId);
    }
  }

  // Пересылка сообщения в твой личный профиль
  private void forwardMessageToAdmin(long chatId, String text) {
    long adminChatId = 1117773461; // Замени на твой ID чата
    SendMessage forwardMessage = new SendMessage();
    forwardMessage.setChatId(String.valueOf(adminChatId));
    forwardMessage.setText("Новое сообщение от пользователя:\n" + text);

    try {
      execute(forwardMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  // Ответ пользователю стандартным сообщением
  private void sendStandardReply(long chatId) {
    SendMessage replyMessage = new SendMessage();
    replyMessage.setChatId(String.valueOf(chatId));
    replyMessage.setText("Спасибо! Принял к сведению.");

    try {
      execute(replyMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  private void sendStartReply(long chatId) {
    SendMessage replyMessage = new SendMessage();
    replyMessage.setChatId(String.valueOf(chatId));
    replyMessage.setText("Чем Вы хотите со мной поделиться?");

    try {
      execute(replyMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return "TamagotchiTelegramBot"; // Замените на имя вашего бота
  }

  @Override
  public String getBotToken() {
    return "7532318568:AAGiAeM1_2zs40x86WvGppJuxC-wFCUEgRY";
  }

  public void register() {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(this);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

  @Value("bot.name")
  private String botUsername;

  @Value("bot.token")
  private String botToken;
}
