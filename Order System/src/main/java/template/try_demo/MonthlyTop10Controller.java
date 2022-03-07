package template.try_demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The class present monthly report showing top 10 items of each type in a bar chart and table view.
 * It provides the user to choose which month he or she wants to check through entering values in the
 * text field.
 */
public class MonthlyTop10Controller implements Initializable {
    @FXML
    private TableView<ItemManagement> Table;
    @FXML
    private TableColumn<ItemManagement, String> colProduct;
    @FXML
    private TableColumn<ItemManagement, Integer> colMonthItem;
    @FXML
    private TableColumn<ItemManagement, Integer> colSales;
    @FXML
    private StackedBarChart barChart_monthlyTop;
    @FXML
     private TextField userInputMonth;
    @FXML
    private Button getTop10Btn;
    @FXML
     private Label warningMsg;
    @FXML
    private  Label searchWariningMsg;

    ArrayList<String> myProductType = new ArrayList<String>();
    ArrayList<String> myItemId = new ArrayList<String>();
    ArrayList<String> mySales = new ArrayList<String>();
    String[] myMonthlyTopProductType = {"Shirt", "Trouser","Jacket"};
    int count3=0;
    ObservableList<ItemManagement> obList2 = FXCollections.observableArrayList();
    @FXML
    private void checkTop10Action(ActionEvent event) throws IOException {
        Connection connection = DatabaseConnection.connect();
        barChart_monthlyTop.setAnimated(false);
        Table.getItems().clear();
        barChart_monthlyTop.getData().clear();

        try {
            if(Integer.parseInt(userInputMonth.getText())<1 || Integer.parseInt(userInputMonth.getText())>12){
                throw new Exception();
            }
            for(int i=0;i<3;i++) {
                ResultSet rs = connection.createStatement().executeQuery(
                        "SELECT shirtData.product, CustomerInfo.product AS myItem, SUM(subTotal) AS Total FROM shirtData,CustomerInfo " +
                                "WHERE shirtData.itemId=CustomerInfo.product AND CustomerInfo.month = "+userInputMonth.getText() +
                                " AND shirtData.product = '"+ myMonthlyTopProductType[i]+"'"+
                        " GROUP BY CustomerInfo.product " +
                                "ORDER by Total DESC " +
                                "LIMIT 10" );

                while (rs.next()) {
                        setDataField(rs);//store data into arraylist
                    count3++;
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(Exception e){
            searchWariningMsg.setText("Invalid Input");
        }
        //dump everything into table.
        //product of the same type should be put nearby each other.
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product2"));
        colMonthItem.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSales.setCellValueFactory(new PropertyValueFactory<>("quantity2"));

        Table.setItems(obList2);

        //draw bar chart
        final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        final XYChart.Series<String, Number> series3 = new XYChart.Series<>();

        //create series instance
        series1.setName("Shirt");
        series2.setName("Trouser");
        series3.setName("Jacket");

            Platform.runLater(() -> {
                // Update chart's name
                barChart_monthlyTop.setTitle("Monthly Top 10");
                // Remove all data
                series1.getData().clear();
                series2.getData().clear();
                series3.getData().clear();
                // Add as many data as you want

                if(myProductType.size() != 0){
                    //warningMsg.setText("");
                    for(int i=0;i<myProductType.size();i++){
                        if (myProductType.get(i).equals("Shirt")) {
                            series1.getData().add(new XYChart.Data<>("item"+myItemId.get(i), Integer.parseInt(mySales.get(i))));
                        }
                        if (myProductType.get(i).equals("Trouser")) {
                            series2.getData().add(new XYChart.Data<>("item"+myItemId.get(i), Integer.parseInt(mySales.get(i))));
                        }
                        if (myProductType.get(i).equals("Jacket")) {
                            series3.getData().add(new XYChart.Data<>("item"+myItemId.get(i), Integer.parseInt(mySales.get(i))));
                        }
                    }
                    barChart_monthlyTop.setData(FXCollections.observableArrayList(series1, series2, series3));
                }
                else{
                    System.out.println("no data in month: "+userInputMonth.getText());
                }

            });




    }
    public void setDataField(ResultSet rs){
        //get product type, item id, and total from database and show on table view
        try{
            ItemManagement monthlyTopData = new ItemManagement();
            monthlyTopData.setProduct2(rs.getString("product"));
            monthlyTopData.setProduct(rs.getString("myItem"));
            monthlyTopData.setQuantity2(rs.getString("Total"));
            obList2.add(monthlyTopData);
            System.out.println(rs.getString("myItem"));
            myProductType.add(rs.getString("product"));
            myItemId.add(rs.getString("myItem"));
            mySales.add(rs.getString("Total"));
        }catch (Exception e){
            System.out.println("something went wrong: "+e);
        }

    }

    //create a refresh button to refresh the page everytime before generating a new report (with different data sets)
    @FXML
    private Button refreshBtn2;
    @FXML
    private void refreshPageAction2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("MonthlyTop10.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 910, 460);
        Stage stage = new Stage();
        stage.setTitle("Quarterly Sales");
        stage.setScene(scene);
        stage.show();
        // get a handle to the stage
        stage = (Stage) refreshBtn2.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
