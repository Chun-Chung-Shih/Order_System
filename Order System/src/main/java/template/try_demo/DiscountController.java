package template.try_demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Random;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//The class create random discount catalog
public class DiscountController implements Initializable {
    @FXML
    private TableView Table;
    @FXML
    private TableColumn colItem;
    @FXML
    private TableColumn colProduct;
    @FXML
    private TableColumn colPrice;
    @FXML
    private TableColumn colSize;
    @FXML
    private TableColumn colColor;
    @FXML
    private TableView Table2;
    @FXML
    private TableColumn colItem3;
    @FXML
    private TableColumn colProduct2;
    @FXML
    private TableColumn colPrice2;
    @FXML
    private TableColumn colSize2;
    @FXML
    private TableColumn colColor2;
    Random rd = new Random(); //create Random instance
    ArrayList<Integer> al=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateRandomDiscount(40);//generate the basic 40 items' random discount report
    }
    public void generateRandomDiscount(int i){
        while(al.size()<=15){ //Total 10 random numbers
            int n=(rd.nextInt(i))+1; //generate 1-40 random number
            if(al.contains(n))
                continue;     //do not duplicate the number
            else
                al.add(n);
            System.out.println(n);
        }
        int count=0;

        Connection connection = DatabaseConnection.connect();
        ObservableList<DiscountInfo> obList4 = FXCollections.observableArrayList();
        ObservableList<DiscountInfo> obList5 = FXCollections.observableArrayList();
        try {

            ResultSet rs5 = connection.createStatement().executeQuery("SELECT * FROM shirtData " +
                    "WHERE itemId = "+al.get(0)+" OR itemId = "+al.get(1)+" OR itemId = "+al.get(2)+" OR itemId = "+al.get(3)
                    +" OR itemId = "+al.get(4)+" OR itemId = "+al.get(5)+" OR itemId = "+al.get(6)+" OR itemId = "+al.get(7)
                    +" OR itemId = "+al.get(8)+" OR itemId = "+al.get(9)+" OR itemId = "+al.get(10)+" OR itemId = "+al.get(11)
                    +" OR itemId = "+al.get(12)+" OR itemId = "+al.get(13)+" OR itemId = "+al.get(14));
            while (rs5.next()) {
                if(rs5.getString("itemId").isEmpty()){
                    throw new Exception();
                }
                if(count%2==0){//add to the 15% table
                    DiscountInfo discountInfo = new DiscountInfo();
                    discountInfo.setProduct3(rs5.getString("itemId"));
                    discountInfo.setProduct(rs5.getString("product"));
                    discountInfo.setPrice((rs5.getDouble("price"))*0.75);
                    discountInfo.setColor(rs5.getString("color"));
                    discountInfo.setSize(rs5.getString("size"));
                    obList4.add(discountInfo);
                }
                else{//add to the 30% table
                    DiscountInfo discountInfo2 = new DiscountInfo();
                    discountInfo2.setProduct3(rs5.getString("itemId"));
                    discountInfo2.setProduct(rs5.getString("product"));
                    discountInfo2.setPrice(Math.round(rs5.getDouble("price")*0.7*100)/100.0);
                    discountInfo2.setColor(rs5.getString("color"));
                    discountInfo2.setSize(rs5.getString("size"));
                    obList5.add(discountInfo2);
                }

                count++;
            }
            rs5.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch(Exception e){
            errorMsg.setText("Invalid input");
        }
        colItem.setCellValueFactory(new PropertyValueFactory<>("product3"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("Color"));
        Table.setItems(obList4);

        colItem3.setCellValueFactory(new PropertyValueFactory<>("product3"));
        colPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProduct2.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSize2.setCellValueFactory(new PropertyValueFactory<>("size"));
        colColor2.setCellValueFactory(new PropertyValueFactory<>("Color"));
        Table2.setItems(obList5);
    }

    @FXML
    private TextField totalItemNumberNow;
    @FXML
    private Label errorMsg;
    @FXML
    public void updateTotalItemNumber(ActionEvent event){//generate user defined range of item id
        errorMsg.setText("");
        al.clear();
        Table.getItems().clear();//clean the current table
        Table2.getItems().clear();//clean the current table
        try{
            int num = Integer.parseInt(totalItemNumberNow.getText());//check if it is integer
            generateRandomDiscount(num);
        } catch (Exception e) {
            //e.printStackTrace();
            errorMsg.setText("Invalid input");
        }

    }

}
