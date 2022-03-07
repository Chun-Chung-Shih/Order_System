package template.try_demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The class shows quarterly sales report to the user. It allows users to specify which quarter he or she wants
 * to check by entering the current month in the text field.
 * The class also provides methods that show revenue, total quantity sold, and total sales to the user.
 * Pie charts and barcharts are also provided through methods in this class.
 */
public class QuarterSalesController implements Initializable {
    @FXML
    private TextField totalQuantityField;
    @FXML
    private TextField totalSalesField;
    @FXML
    private TextField totalRevenueField;
    @FXML
    private TextField currentMonthLabel2;
    @FXML
    private Button checkMonthBtn;
    @FXML
    private PieChart pieChart1;
    @FXML
    private PieChart pieChart2;
    @FXML
    private PieChart pieChart3;
    @FXML
    private BarChart barChart;
    @FXML
    private TableView Table;
    @FXML
    private TableColumn colItemId;
    @FXML
    private TableColumn colSales;
    @FXML
    private TableColumn colQuantity;
    @FXML
    private TableColumn colMonth;
    @FXML
    private StackedAreaChart<Integer, Integer> StackedAreaChart;
    @FXML
    private StackedBarChart stackedBarChart;
    @FXML
    private Label warningMsg;

    String monthLabel="jan";
    int startMonth=12;
    int checkMonth;
    //Calculate Total Volume (click the calculate summary button)
    @FXML
            private void calculateTotal(ActionEvent evenet){
        Connection connection = DatabaseConnection.connect();
        try{
            System.out.println(checkMonth);
            String s = "SELECT SUM(shirtData.Revenue*CustomerInfo.quantity) AS revenuePerItem, " +
                    "SUM(CustomerInfo.quantity) AS TotalQuantity, CustomerInfo.month, " +
                    "SUM(CustomerInfo.subTotal) AS Total FROM CustomerInfo, shirtData " +
                    "WHERE month = "+checkMonth+" AND shirtData.itemId=CustomerInfo.product " +
                    "OR month = "+(checkMonth+1) +" AND shirtData.itemId=CustomerInfo.product " +
                    "OR month = "+(checkMonth+2) +" AND shirtData.itemId=CustomerInfo.product";
            ResultSet rs = connection.createStatement().executeQuery(
                    s);
            while (rs.next()) {
                totalQuantityField.setText(rs.getString("TotalQuantity"));
                totalSalesField.setText(rs.getString("Total"));
                totalRevenueField.setText((rs.getString("revenuePerItem")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ObservableList<StockData> obList12 = FXCollections.observableArrayList();
    HashMap<String, Integer> IdAndSalesList1 = new HashMap<>();
    HashMap<String, Integer> IdAndSalesList2 = new HashMap<>();
    HashMap<String, Integer> IdAndSalesList3 = new HashMap<>();
    @FXML
    private void checkMonthlySalesAction(ActionEvent event) throws IOException {
        Connection connection = DatabaseConnection.connect();
        String monthLabel="jan";
        int count2=0;//count how many times the loop runs

        try {

            int userDefinedMonth2 = Integer.parseInt(currentMonthLabel2.getText());
            System.out.println(userDefinedMonth2);
            if (userDefinedMonth2 > 3 && userDefinedMonth2 < 7) {
                userDefinedMonth2 = 3;
                System.out.println("Enter 3<x<7");
            } else if (userDefinedMonth2 > 6 && userDefinedMonth2 < 10) {
                userDefinedMonth2 = 6;
            } else if (userDefinedMonth2 > 9 && userDefinedMonth2 <= 12) {
                userDefinedMonth2 = 9;
            } else if (userDefinedMonth2 <= 3 && userDefinedMonth2 >= 1) {
                userDefinedMonth2 = 0;
                System.out.println("Enter <=3");
            } else {
                throw new Exception();

            }
            String[] myMonthList = new String[3];
            System.out.println(userDefinedMonth2);

            ArrayList<Integer> checkMonthList = new ArrayList<>();
            ArrayList<String> myMonthItem1 = new ArrayList<>();
            ArrayList<String> myMonthItem2 = new ArrayList<>();
            ArrayList<String> myMonthItem3 = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                System.out.println(userDefinedMonth2);
                checkMonth = userDefinedMonth2 - i;

                //check if the entered value is valid or not
                if (checkMonth == 0) {
                    checkMonth = 12;
                }
                if (checkMonth == -1) {
                    checkMonth = 11;
                }
                if (checkMonth == -2) {
                    checkMonth = 10;
                }
                switch (checkMonth) {//turn number into name of the month
                    case 1:
                        monthLabel = "Jan";
                        break;
                    case 2:
                        monthLabel = "Feb";
                        break;
                    case 3:
                        monthLabel = "Mar";
                        break;
                    case 4:
                        monthLabel = "April";
                        break;
                    case 5:
                        monthLabel = "May";
                        break;
                    case 6:
                        monthLabel = "Jun";
                        break;
                    case 7:
                        monthLabel = "Jul";
                        break;
                    case 8:
                        monthLabel = "Aug";
                        break;
                    case 9:
                        monthLabel = "Sep";
                        break;
                    case 10:
                        monthLabel = "Oct";
                        break;
                    case 11:
                        monthLabel = "Nov";
                        break;
                    case 12:
                        monthLabel = "Dec";
                        break;
                }
                myMonthList[i] = monthLabel;
                checkMonthList.add(checkMonth);

                try {
                    //read from database
                    ResultSet rs = connection.createStatement().executeQuery(
                            "SELECT product, SUM(quantity) AS TotalQuantity, month, SUM(subTotal) AS Total FROM CustomerInfo " +
                                    "WHERE CustomerInfo.month= '" + checkMonthList.get(i) + "' "
                                    + "GROUP BY product ORDER by product ASC");
                    while (rs.next()) {
                        StockData stockData = new StockData();
                        stockData.setProduct2(rs.getString("product"));//item id
                        stockData.setSales(rs.getInt("Total"));//total sales per each item id per month
                        stockData.setQuantity(rs.getString("TotalQuantity"));//total quantity per id per month
                        stockData.setMonth(rs.getInt("month"));
                        obList12.add(stockData);
                        if (count2 == 0) {//storing different months into different arraylist
                            IdAndSalesList1.put(rs.getString("product"), rs.getInt("Total"));
                            myMonthItem1.add(rs.getString("product"));
                            System.out.println(rs.getString("product") + " In the result set");
                        } else if (count2 == 1) {
                            IdAndSalesList2.put(rs.getString("product"), rs.getInt("Total"));
                            myMonthItem2.add(rs.getString("product"));
                        } else if (count2 == 2) {
                            IdAndSalesList3.put(rs.getString("product"), rs.getInt("Total"));
                            myMonthItem3.add(rs.getString("product"));
                        } else {
                            throw new Exception();
                        }

                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Error in matching month and sales.");
                }

                count2++;
            }
            colItemId.setCellValueFactory(new PropertyValueFactory<>("product2"));
            colSales.setCellValueFactory(new PropertyValueFactory<>("sales"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            Table.setItems(obList12);


            int month1 = myMonthItem1.size();
            int month2 = myMonthItem2.size();
            int month3 = myMonthItem3.size();
            System.out.println(month1 + ", " + month2 + ", " + month3);

            //draw pie chart (3 months)
            for (int i = 0; i < month1; i++) {
                ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList(new PieChart.Data
                        (myMonthItem1.get(i), IdAndSalesList1.get(myMonthItem1.get(i))));
                pieChart1.getData().addAll(pieChartData1);

                System.out.println(i + "  " + myMonthItem1.get(i) + "  " + IdAndSalesList1.get(myMonthItem1.get(i)));

            }
            for (int j = 0; j < month2; j++) {
                ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(new PieChart.Data
                        (myMonthItem2.get(j), IdAndSalesList2.get(myMonthItem2.get(j))));
                pieChart2.getData().addAll(pieChartData2);
            }
            for (int k = 0; k < month3; k++) {
                ObservableList<PieChart.Data> pieChartData3 = FXCollections.observableArrayList(new PieChart.Data
                        (myMonthItem3.get(k), IdAndSalesList3.get(myMonthItem3.get(k))));
                pieChart3.getData().addAll(pieChartData3);
            }

            pieChart1.setTitle("Sales per item from " + myMonthList[0]);
            pieChart2.setTitle("Sales per item from " + myMonthList[1]);
            pieChart3.setTitle("Sales per item from " + myMonthList[2]);

            //create a bar chart of the report

            XYChart.Series<String, Integer> series1 = new XYChart.Series();
            XYChart.Series<String, Integer> series2 = new XYChart.Series();
            XYChart.Series<String, Integer> series3 = new XYChart.Series();
            //create series instance
            series1.setName(myMonthList[0]);
            for (int i = 0; i < myMonthItem1.size(); i++) {
                series1.getData().add(new XYChart.Data<>(myMonthItem1.get(i), IdAndSalesList1.get(myMonthItem1.get(i))));
            }

            series2.setName(myMonthList[1]);
            for (int i = 0; i < myMonthItem2.size(); i++) {
                series2.getData().add(new XYChart.Data<>(myMonthItem2.get(i), IdAndSalesList2.get(myMonthItem2.get(i))));
            }

            series3.setName(myMonthList[2]);
            for (int i = 0; i < myMonthItem3.size(); i++) {
                series3.getData().add(new XYChart.Data<>(myMonthItem3.get(i), IdAndSalesList3.get(myMonthItem3.get(i))));
            }
            stackedBarChart.getData().addAll(series1, series2, series3);
        }catch(Exception e){
            warningMsg.setText("Invalid Input");
        }



    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private Button refreshBtn;
    @FXML
    private void refreshPageAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("QuaterlySales.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 680);
        Stage stage = new Stage();
        stage.setTitle("Quarterly Sales");
        stage.setScene(scene);
        stage.show();
        // get a handle to the stage
        stage = (Stage) refreshBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
