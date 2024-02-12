package omega.service.Spring1cBot.config;

import lombok.Data;
import lombok.Getter;
import omega.service.Spring1cBot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot(token, botName);
    }
}
