package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
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

    double w = 300;
    double h = 275;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane p = new Pane();
        Text label = new Text("Welcome");
        label.setFont(Font.font("Segoe UI", 30));
        label.setLayoutX(w/2 - (label.getLayoutBounds().getWidth()/2));
        label.setLayoutY(h/2 - 20);
        Text label1 = new Text("Enter your discord token to connect");
        label1.setFont(Font.font("Segoe UI", 13));
        label1.setLayoutX(w/2 - label1.getLayoutBounds().getWidth()/2);
        label1.setLayoutY(h/2);
        TextField field = new TextField();
        field.setPrefWidth(200);
        field.setPromptText("Token");
        field.setLayoutX(w/2 - field.getPrefWidth()/2);
        field.setLayoutY(h - 30);
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            w = newValue.doubleValue();
            System.out.println("W: " + newValue);
            // add thing that need to be reposotioned here
            label.setLayoutX(w/2 - (label.getLayoutBounds().getWidth()/2));
            label1.setLayoutX(w/2 - label1.getLayoutBounds().getWidth()/2);
        });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            h = newValue.doubleValue();
            System.out.println("H: " + newValue);
            // add thing that need to be reposotioned here
            //field.setLayoutY(h);
            label1.setLayoutY(h/2);
            label.setLayoutY(h/2 - 20);
        });
        //
        //
        p.getChildren().addAll(label,field,label1);
        primaryStage.setTitle("Discord");
        primaryStage.setScene(new Scene(p, w, h));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private StackPane setupLogin(Stage primaryStage) {
        Label label = new Label("Welcome");
        label.setScaleX(2.0);
        label.setScaleY(2.0);
        label.setScaleZ(2.0);
        Label label1 = new Label("Enter your discord token to connect");
        label1.setTranslateY(20.0d);
        TextField field = new TextField();
        field.setPromptText("Token");
        field.setMaxWidth(50);
        field.setTranslateY(h - 155d);
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            w = newValue.doubleValue();
            System.out.println("W: " + newValue);
            // add thing that need to be reposotioned here
        });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            h = newValue.doubleValue();
            System.out.println("H: " + newValue);
            // add thing that need to be reposotioned here
            field.setTranslateY(h);
        });
        //
        StackPane pane = new StackPane();
        pane.getChildren().addAll(label, label1, field);
        return pane;
    }
}
