package com.example.optimization;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchto1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("phase1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchto2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("phase2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
