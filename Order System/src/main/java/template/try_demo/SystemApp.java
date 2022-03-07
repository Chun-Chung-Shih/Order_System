package template.try_demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Chun-Chung Shih
 * @since 11.10.2021
 * The program is a desktop-based order entry system for Adelaide Premium Shirts.
 * The order entry system sells Shirts, Trousers, and Jackets.
 * As the sales rep enters the order, each item’s availability (quantity-on-hand) is verified.
 * If one or more items are not currently available (in Adelaide Premium’s warehouse), then the item is marked as
 * back-ordered until the items arrive at the warehouse. After all of the items are available, payment is verified
 * and the order is submitted to the warehouse for assembly and for shipping to the customer’s address.
 * When the order is received, the customer is given an order ID, which is used to track the order throughout the process.
 */
public class SystemApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 530);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}