package template.try_demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The class shows four buttons on the window and would show up different pages
 * once the user click on once of them
 */
public class MainMenuController {
    @FXML
    private Button browseItemBtn;
    @FXML
    private Button EnterOrderBtn;
    @FXML
    private Button generateCatalogBtn;
    @FXML
    private Button generateReportBtn;

    @FXML
    private void enterOrderAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("EnterOrder.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 510);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void browseItemAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("CatalogManagement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 810, 650);
        Stage stage = new Stage();
        stage.setTitle("Browse Item");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void generateReportAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("ReportGeneration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Generate Report");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void generateCatalogAction(ActionEvent event) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("RandomDiscount.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 810, 530);
            Stage stage = new Stage();
            stage.setTitle("Discount!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You clicked btn2!");
    }

}
