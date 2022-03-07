package template.try_demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * The class show the user what he has just added into the order and related customer information.
 * It asks the user to input credit card information and store the information into database.
 */
public class CreditCardController implements Initializable {
    @FXML
    private Button verifyName;
    @FXML
    private Button creditCardEnter;
    @FXML
    private Button quitBtn;
    @FXML
    private Button showInfoBtn;
    @FXML
    private TextField customerName_pay;

    @FXML
    private TextField OrderId_pay;
    @FXML
    private TextField paymentAmount_pay;
    @FXML
    private TextField orderMonth_pay;
    @FXML
    private TextField orderCustomerId_pay;
    @FXML
    private TextField creditCardType_pay;
    @FXML
    private TextField creditCardNumber_pay;
    @FXML
    private TextField creditCardDate_pay;
    @FXML
    private TextField numberOfPieces_pay;
    @FXML
    private Label remindMsg_pay;
    @FXML
    private TableView<ItemManagement> Table;
    @FXML
    private TextField deliveryDate_pay;
    @FXML
    private TableColumn ItemTypeCol;
    @FXML
    private TableColumn productTypeCol;
    @FXML
    private TableColumn quantityCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn availCol;


    ObservableList<ItemManagement> obList3 = FXCollections.observableArrayList();
    /**
     * The method load data from database and show what the customer has added into this order
     * @throws IOException
     */
    @FXML
    protected void showCustomerOrderInfo() throws IOException {
        Connection connection = DatabaseConnection.connect();
        System.out.println("Enter CreditCardController's showInfo1");
        String sql;
        try {
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            sql = "INSERT INTO OrderInfo(customerName, orderCount, orderID) VALUES ('" + orderCustomerId_pay.getText() + "', " +
                    "((SELECT MAX(orderCount) FROM OrderInfo) +1),'" + OrderId_pay.getText() +"' )";
            stmt.executeUpdate(sql);

           //add month
            sql = "UPDATE OrderInfo SET SetOrderMonth = '" + orderMonth_pay.getText() + "' " +
                    "WHERE orderCount = (Select Max(orderCount) FROM OrderInfo ) ";
            stmt.executeUpdate(sql);
            for(int i = 0; i<Integer.parseInt(numberOfPieces_pay.getText()); i++){

                sql = "UPDATE CustomerInfo SET month = '" + orderMonth_pay.getText() + "' " +
                        "WHERE orderCount = (Select Max(orderCount) FROM CustomerInfo )- "+i;
                stmt.executeUpdate(sql);
            }


            //add OrderId into OrderInfo table and CustomerInfo table
            for(int i = 0; i<Integer.parseInt(numberOfPieces_pay.getText()); i++) {
                sql = "UPDATE CustomerInfo SET orderID = '" + OrderId_pay.getText() + "' " +
                        "WHERE orderCount = (Select Max(orderCount) FROM CustomerInfo )- "+i;
                stmt.executeUpdate(sql);
            }
            stmt.close();
            connection.commit();

            ResultSet rs3 = connection.createStatement().executeQuery("SELECT CustomerInfo.customerName, " +
                    "CustomerInfo.product AS itemId, CustomerInfo.quantity, CustomerInfo.price, shirtData.product, shirtData.quantity2, " +
                    "CustomerInfo.orderCount " +
                    "FROM CustomerInfo, shirtData "+
                    "WHERE customerName = '"+orderCustomerId_pay.getText()+"' AND CustomerInfo.product=shirtData.itemId " +
                    "AND CustomerInfo.orderId = '"+OrderId_pay.getText()+"' " +
                    "ORDER BY orderCount DESC LIMIT "+numberOfPieces_pay.getText());
            while (rs3.next()) {
                //show what the customer has just ordered in this order on the window
                ItemManagement orderInfo = new ItemManagement();
                orderInfo.setProduct2(rs3.getString("itemId"));
                orderInfo.setProduct(rs3.getString("product"));
                orderInfo.setQuantity(rs3.getInt("quantity"));
                orderInfo.setPrice(rs3.getInt("price"));
                if(!rs3.getString("quantity2").equals("back-ordered")){
                    orderInfo.setDescription("available");
                }
                else{
                    orderInfo.setDescription(rs3.getString("quantity2"));
                }
                obList3.add(orderInfo);
            }
            rs3.close();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ItemTypeCol.setCellValueFactory(new PropertyValueFactory<>("product2"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTypeCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        availCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        Table.setItems(obList3);

    }

    /**
     * Once the user finish entering the credit card info and finish the order, the window will be closed,
     * and the confirmation message will show up to confirm the creation of the order.
     * @throws IOException
     */
    @FXML
    protected void onProcessButtonClick() throws IOException {
        Connection connection = DatabaseConnection.connect();
        String sql;
        try {

            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            //store credit card information
            sql = "UPDATE OrderInfo SET creditCardType = '"+creditCardType_pay.getText()+"' " +
                    "WHERE orderCount = (Select Max(orderCount) FROM OrderInfo )";
            stmt.executeUpdate(sql);

            sql = "UPDATE OrderInfo SET creditCardNumber = '"+creditCardNumber_pay.getText()+"' " +
                    "WHERE orderCount = (Select Max(orderCount) FROM OrderInfo )";
            stmt.executeUpdate(sql);

            sql = "UPDATE OrderInfo SET creditCardDate = '"+creditCardDate_pay.getText()+"' " +
                    "WHERE orderCount = (Select Max(orderCount) FROM OrderInfo )";
            stmt.executeUpdate(sql);


            sql = "UPDATE OrderInfo SET payment = '"+paymentAmount_pay.getText()+"' " +
                    "WHERE orderCount = (Select Max(orderCount) FROM OrderInfo )";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            stmt.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("ConfirmOrderMessage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 170);
            Stage stage = new Stage();
            stage.setTitle("Confirm");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        // get a handle to the stage
        Stage stage = (Stage) creditCardEnter.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * The method calculate the total payment amount for this order and show on the screen
     * @throws IOException
     */
    @FXML
    protected void onVerifyNameButtonClick() throws IOException {
        Connection connection = DatabaseConnection.connect();

        try {
            //calculate total amount
            int addNum=0;
            ResultSet rs2 = connection.createStatement().executeQuery("SELECT subTotal, quantity, CustomerName, " +
                    "orderCount FROM CustomerInfo " +
                    "WHERE CustomerInfo.customerName=  " +orderCustomerId_pay.getText()+" "+
                    "ORDER by orderCount DESC limit " +numberOfPieces_pay.getText());
            while(rs2.next()){
                addNum=addNum+(rs2.getInt("subTotal"));
            }
            paymentAmount_pay.setText("$"+addNum);
            rs2.close();

                   connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        deliveryDate_pay.setText(orderMonth_pay.getText()+" / "+(int)(Math.random()*29+1)+" / "+"2021");
    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerName_pay.setEditable(false);
        OrderId_pay.setEditable(false);
        orderMonth_pay.setEditable(false);
        paymentAmount_pay.setEditable(false);
        orderCustomerId_pay.setEditable(false);

        }

        //The method pass value from what the user has input in the previous enter order page to this page
    public void showInformation(String name, String Order, String month, String location, Integer count){
        customerName_pay.setText(name);
        OrderId_pay.setText(Order);
        orderMonth_pay.setText(month);
        orderCustomerId_pay.setText(location);
        numberOfPieces_pay.setText(String.valueOf(count));

    }

    }