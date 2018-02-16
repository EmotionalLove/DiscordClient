package com.discclient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.dv8tion.jda.client.entities.Friend;
import net.dv8tion.jda.client.entities.RelationshipType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

import static com.discclient.DiscordHandler.jda;

public class Main extends Application {

    static double w = 325;
    static double h = 275;
    static String orignalProperty;
    static Stage primaryStage;
    static Stage otherStage;
    private static Guild currentServer;

    public static TextField tokenField;
    public static ListView<String> listView;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setScene(setupLogin());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Scene setupDiscord() {
        primaryStage.hide();
        w = 700;
        h = 600;
        Pane p = new Pane();
        Scene s = new Scene(p, w, h);
        MenuButton menu = new MenuButton("Servers");
        menu.setLayoutX(w - w + 15);
        menu.setPrefWidth(150);
        for (Guild g : jda.getGuilds()) {
            MenuItem item = new MenuItem(g.getName());
            item.setOnAction(e-> {
                menu.setText(g.getName());
                currentServer = g;
            });
            menu.getItems().add(item);
        }
        MenuButton cmenu = new MenuButton("Channels");
        cmenu.setLayoutX(w-w + 170);
        cmenu.setPrefWidth(150);
        ListView<String> listView = new ListView<>();
        listView.setPrefWidth(w);
        listView.setPrefHeight(h-130);
        listView.setLayoutY(80);
        ObservableList<Friend> list = FXCollections.observableArrayList();
        for (Friend usr : jda.asClient().getFriends()) {
            list.add(usr);
        }
        listView.setItems(copyArrFriend(list));
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deFriend = new MenuItem("remove friend");
            deFriend.setOnAction(e -> {
                try {
                    String item = cell.getItem();
                    PrivateChannel channel = list.get(cell.getIndex()).getUser().openPrivateChannel().submit().get();
                    list.remove(cell.getIndex());
                    listView.setItems(copyArrFriend(list));
                }catch (Exception de) {
                    de.printStackTrace();
                }
            });
            contextMenu.getItems().addAll(deFriend);
            cell.textProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
        //
        s.widthProperty().addListener((observable, oldValue, newValue) -> {
            w = newValue.doubleValue();
            // add thing that need to be reposotioned here
            menu.setLayoutX(w - w + 15);
            cmenu.setLayoutX(w-w +  170);
            listView.setPrefWidth(w);
        });
        s.heightProperty().addListener((observable, oldValue, newValue) -> {
            h = newValue.doubleValue();
            // add thing that need to be reposotioned here
            menu.setLayoutY(10);
            cmenu.setLayoutY(10);
            listView.setPrefHeight(h-130);
        });
        p.getChildren().addAll(menu,cmenu, listView);
        s.setRoot(p);
        primaryStage.setScene(s);
        primaryStage.show();
        return s;
    }

    public static void updateListView() {
        ObservableList<Message> list = FXCollections.observableArrayList();

    }

    public static ObservableList<String> copyArrFriend(ObservableList<Friend> arr) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Friend fr : arr) {
            list.add(fr.getUser().getName());
        }
        return list;
    }

    public static Scene setupLoggingIn() {
        Stage other = new Stage();
        Pane p = new Pane();
        Scene s = new Scene(p, 250, 200);
        other.setResizable(false);
        other.setAlwaysOnTop(true);
        Text txt = new Text("Logging into Discord");
        txt.setFont(Font.font("Segoe UI", 16));
        txt.setLayoutX(250/2 - txt.getLayoutBounds().getWidth()/2);
        txt.setLayoutY(200/2);
        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(150);
        bar.setLayoutX(250/2 - 150/2);
        bar.setLayoutY(200/2 + 50);
        bar.setProgress(-1);
        p.getChildren().addAll(txt, bar);
        other.setScene(s);
        other.show();
        otherStage = other;
        return s;
    }

    public static void stopLoggingIn() {
        otherStage.close();
    }

    private static Scene setupLogin() {
        Pane p = new Pane();
        Scene s = new Scene(p, w, h);
        Text label = new Text("Welcome");
        label.setFont(Font.font("Segoe UI", 30));
        label.setLayoutX(w/2 - (label.getLayoutBounds().getWidth()/2));
        label.setLayoutY(h/2 - 20);
        Text label1 = new Text("Enter your discord token to connect");
        label1.setFont(Font.font("Segoe UI", 13));
        label1.setLayoutX(w/2 - label1.getLayoutBounds().getWidth()/2);
        label1.setLayoutY(h/2);
        Button button = new Button("Login");
        button.setDisable(true);
        button.setLayoutY(h - 30);
        TextField field = new TextField();
        field.setPrefWidth(200);
        orignalProperty = field.getStyle();
        field.setPromptText("Token");
        tokenField = field;
        field.textProperty().addListener(e -> {
            if (field.getText().length() == 0) {
                button.setDisable(true);
                field.setStyle(orignalProperty);
                return;
            }
            field.setStyle(orignalProperty);
            button.setDisable(false);
        });
        field.setLayoutX(w/2 - field.getPrefWidth()/2);
        field.setLayoutY(h - 30);
        button.setLayoutX(field.getLayoutX() + field.getPrefWidth() + 10);


        button.setOnAction(e -> {
            try {
                DiscordHandler.connect(field.getText());
                System.out.println("Connecting to Discord.");
            }catch (Exception ex) {
                System.out.println("Connection failed.");
                field.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:rgba(255,0,0,0.7);");
                field.requestFocus();
            }
        });

        s.widthProperty().addListener((observable, oldValue, newValue) -> {
            w = newValue.doubleValue();
            // add thing that need to be reposotioned here
            label.setLayoutX(w/2 - (label.getLayoutBounds().getWidth()/2));
            field.setLayoutX(w/2 - field.getPrefWidth()/2);
            label1.setLayoutX(w/2 - label1.getLayoutBounds().getWidth()/2);
            button.setLayoutX(field.getLayoutX() + field.getPrefWidth() + 10);
        });
        s.heightProperty().addListener((observable, oldValue, newValue) -> {
            h = newValue.doubleValue();
            // add thing that need to be reposotioned here
            label1.setLayoutY(h/2);
            label.setLayoutY(h/2 - 20);
            field.setLayoutY(h - 30);
            button.setLayoutY(h - 30);
        });
        p.getChildren().addAll(label,field,label1, button);
        s.setRoot(p);
        return s;
    }
}
