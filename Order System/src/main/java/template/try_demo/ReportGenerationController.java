package template.try_demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportGenerationController {
    @FXML
    private Button stockTakeBtn;
    @FXML
    private Button quarterlyBtn;
    @FXML
    private Button customerDemographicBtn;
    @FXML
    private Button monthlyReportBtn;

    //create new scene for stockTakeReport
    @FXML
    private void stockTakeAction(ActionEvent event) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("StockTakeReport.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 610, 400);
            Stage stage = new Stage();
            stage.setTitle("Stocktake Report!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You clicked btn3!");
    }

    //create new scene for Quaterly sales report
    @FXML
    private void quarterlyAction(ActionEvent event) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("QuaterlySales.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 960, 720);
            Stage stage = new Stage();
            stage.setTitle("Quaterly Sales Report!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You clicked btn4!");
    }
    //create new scene for demographic report
    @FXML
    private void customerDemoAction(ActionEvent event) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("CustomerDemographic.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 610, 450);
            Stage stage = new Stage();
            stage.setTitle("Customer Demographic Report!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You clicked btn4!");
    }
    //create new scene for monthly top 10 report
    @FXML
    private void monthlyReportAction(ActionEvent event) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("MonthlyTop10.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 910, 460);
            Stage stage = new Stage();
            stage.setTitle("Monthly Top 10 Report!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You clicked btn4!");
    }


}
