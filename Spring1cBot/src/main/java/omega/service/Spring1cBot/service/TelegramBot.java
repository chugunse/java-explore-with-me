package omega.service.Spring1cBot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final Connector connector = new Connector();


    public TelegramBot(String token, String name) {
        super(token);
        this.botToken = token;
        this.botName = name;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "поздороваться"));
        listofCommands.add(new BotCommand("/test", "попытка"));
        listofCommands.add(new BotCommand("/deletedata", "delete my data"));
        listofCommands.add(new BotCommand("/close", "закрыть соединение с 1С"));
        listofCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update);
                    break;
                case "/test":
                    getData(chatId, update);
                    break;
                case "/close":
                    connector.closeConnection();
                    break;
                default:
                    sendMessege(chatId, "пока таких команд нету");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void startCommandReceived(Long chatId, Update update) {
        String answer = "Hi " + update.getMessage().getChat().getFirstName();
        sendMessege(chatId, answer);
    }

    private void getData(Long chatId, Update update) {
        String answer = "Hi data" + connector.testConnection();
        sendMessege(chatId, answer);
    }

    private void sendMessege(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ошибка телеграмм api {}", e.getMessage());
        }
    }
}
