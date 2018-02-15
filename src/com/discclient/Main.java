package com.discclient;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    double w = 325;
    double h = 275;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(setupLogin(primaryStage));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }











    private Scene setupLogin(Stage primaryStage) {
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
        button.setLayoutY(h - 30);
        TextField field = new TextField();
        field.setPrefWidth(200);
        field.setPromptText("Token");
        field.setOnKeyTyped(e -> {
            if (field.getText().length() == 0) {
                button.setDisable(true);
                return;
            }
            button.setDisable(false);
        });
        field.setLayoutX(w/2 - field.getPrefWidth()/2);
        field.setLayoutY(h - 30);
        button.setLayoutX(field.getLayoutX() + field.getPrefWidth() + 10);

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
