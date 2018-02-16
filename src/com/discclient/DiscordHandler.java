package com.discclient;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordHandler {

    public static JDA jda = null;

    public static void connect(String token) throws LoginException, RateLimitedException, InterruptedException {
        JDA jda = new JDABuilder(AccountType.CLIENT).setToken(token).buildBlocking();
        jda.addEventListener(new MessageListener());
        DiscordHandler.jda = jda;
        Main.setupDiscord();
    }

}
class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

    }
}
