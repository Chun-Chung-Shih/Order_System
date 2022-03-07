package template.try_demo;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The class shows the customer demographic report, which present the relationship between customer
 * ages, sales of each age per product types, and three product types.
 */
public class CustomerDemograController implements Initializable {
    @FXML
    private StackedBarChart stackedBarChart2;
    @FXML
    private PieChart pieChart;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label percentLabel;
    @FXML
    private void handlePieButtonAction(ActionEvent event){

    }
    private int[] numList = new int[4];

    private ArrayList<Integer> myAge1 = new ArrayList<>();
    private ArrayList<Integer> mySales1 = new ArrayList<>();

    private ArrayList<Integer>myAge2 = new ArrayList<>();
    private ArrayList<Integer> mySales2 = new ArrayList<>();

    private ArrayList<Integer> myAge3 = new ArrayList<>();
    private ArrayList<Integer> mySales3 = new ArrayList<>();
    int count =0;
    private final Set<String> categories = new LinkedHashSet<>();//add this !
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection connection = DatabaseConnection.connect();

        try {

            String sql3 = "SELECT shirtData.product AS productType, CustomerInfo.customerName, CustomerInfo.subTotal " +
                    "AS sales,MembershipInfo.age, CustomerInfo.product AS productId FROM CustomerInfo, MembershipInfo, shirtData " +
                    "WHERE CustomerInfo.customerName=MembershipInfo.id AND shirtData.itemId = CustomerInfo.product " +
                    "ORDER BY MembershipInfo.age ASC";
            ResultSet rs = connection.createStatement().executeQuery(sql3);
            while (rs.next()) {
                //store information to different arraylist per product type
                if(rs.getString("productType").equals("Shirt")){
                    myAge1.add(rs.getInt("age"));
                    mySales1.add( rs.getInt("sales"));
                    System.out.println(rs.getString("productType")+" "+rs.getInt("age")+rs.getString("sales"));
                }
                else if(rs.getString("productType").equals("Trouser")){
                    myAge2.add(rs.getInt("age"));
                    mySales2.add( rs.getInt("sales"));
                }

                else if(rs.getString("productType").equals("Jacket")){
                    myAge3.add(rs.getInt("age"));
                    mySales3.add( rs.getInt("sales"));
                }
                else {
                    System.out.println(rs.getString("productType")+" "+rs.getInt("age"));
                    System.out.println("Please check CustomerInfo Database");
                }
                System.out.println(myAge1.size());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //draw stacked bar chart
        XYChart.Series<String, Integer> series1 = new XYChart.Series();
        XYChart.Series<String, Integer> series2 = new XYChart.Series();
        XYChart.Series <String, Integer>series3 = new XYChart.Series();
        final CategoryAxis xAxis = new CategoryAxis();
        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //create series instance
        series1.setName("Shirt");
        series2.setName("Trouser");
        series3.setName("Jacket");
        System.out.println(myAge1.size());
        for(int i =0; i<myAge1.size(); i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(myAge1.get(i)), mySales1.get(i)));
        }
        for(int j = 0; j <myAge2.size(); j++) {
            series2.getData().add(new XYChart.Data<>(String.valueOf(myAge2.get(j)), mySales2.get(j)));
         }
        for(int k = 0; k <myAge3.size(); k++) {
            series3.getData().add(new XYChart.Data<>(String.valueOf(myAge3.get(k)), mySales3.get(k)));
            System.out.println("3");
        }

        stackedBarChart2.getData().addAll(series1, series2, series3);
       // ObservableList<String> list = xAxis.getCategories();
       // Comparator<String> byValue = (e1, e2) -> Integer.compare(Integer.parseInt(e1), Integer.parseInt(e2));
       // list.sort(byValue);

    }
    }
