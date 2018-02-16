package com.discclient;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;


public class DiscordHandler {

    public static JDA jda;

    public static void connect(String token) throws Exception {
        Main.setupLoggingIn();
        new Thread(() -> {
            try {
                jda = new JDABuilder(AccountType.CLIENT).setToken(token).buildBlocking(JDA.Status.CONNECTING_TO_WEBSOCKET);
            } catch (LoginException | InterruptedException e) {
                Platform.runLater(Main::stopLoggingIn);
                Main.tokenField.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:rgba(255,0,0,0.7);");
                Platform.runLater(Main.tokenField::requestFocus);
                e.printStackTrace();
                return;
            }
            jda.addEventListener(new MessageListener());
            Platform.runLater(Main::stopLoggingIn);
            Platform.runLater(Main::setupDiscord);
        }).start();
    }

}
class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

    }
}
