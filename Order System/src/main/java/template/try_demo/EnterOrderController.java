package template.try_demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The EnterOrderController class shows a window that allows the user to input order information
 * , browse items, check availability of an item and finalise the order.
 * The input info will also be automatically written into the database at the same time.
 */
public class EnterOrderController implements Initializable {
    private CustomerData selectedCustomer;
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<ItemManagement> tableProduct;
    @FXML
    private TableColumn<ItemManagement, String> colID1;
    @FXML
    private TableColumn<ItemManagement, String> colProduct;
    @FXML
    private TableColumn<ItemManagement, String> colSize;
    @FXML
    private TableColumn<ItemManagement, String> colColor;
    @FXML
    private TableColumn<ItemManagement, Integer> colPrice;
    @FXML
    private TableColumn<ItemManagement, Integer> colQuantity;
    @FXML
    private TextField itemIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField searchValue_input;
    @FXML
    private TextField orderMonthField;
    @FXML
    private Button orderButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button quitBtn;
    @FXML
    private  Label searchWariningMsg;
    ObservableList<ItemManagement> obList = FXCollections.observableArrayList();
    @FXML
    private Label productAvailability;
    @FXML
    private Label productAvailabilityOK;
    ObservableList<ItemManagement> obList3 = FXCollections.observableArrayList();
    @FXML
    private Label errorIdMsg;
    @FXML
    private Label errorNameMsg;
    @FXML
    private Label errorQuantityMsg;
    @FXML
    private Label errorMonthMsg;
    @FXML
    private Label errorSomeWhereMsg;

    //create button action for main menu

    //create new enter order scene
    @FXML
    private void handleButton1Action(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("EnterOrder.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 510);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        // get a handle to the stage
        stage = (Stage) quitBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //add 1 to the countOrderItem once the add item button is clicked
    private int countOrderItem;
    //check if the input value exist in the current database or not
    public void checkIfValidSearch(String sql){
        try{
            loadDataToOrderPage(sql
                    + searchValue_input.getText()+"'");
            searchWariningMsg.setText(" ");
            if(tableProduct.getItems().isEmpty()){
                searchWariningMsg.setText("Invalid Input.");
            }
        } catch (Exception e) {
            searchWariningMsg.setText("Invalid Input.");
        }

    }

    private ArrayList<String> membershipCount = new ArrayList<>();//count how many membership are in the list now

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String sql = "SELECT * FROM shirtData";
        //show data
       loadDataToOrderPage(sql);
        String sql2 = "SELECT * FROM MembershipInfo";
        Connection connection = DatabaseConnection.connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql2);
            while (rs.next()) {
                membershipCount.add(rs.getString("first_Name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Cannot do this");
        }
        //receive user input order info
        addItemButton.setOnAction(this::handleOrder);
        orderButton.setOnAction(this::finishOrder);
    }
    //add filter button to filter user input value
    //filter item ID
    @FXML
    public void filterID (ActionEvent event) {
        String sql = "SELECT * FROM shirtData WHERE itemId = '";
        checkIfValidSearch(sql);
    }
    //filter item product type
    @FXML
    public void filterType (ActionEvent event) {
        String sql = "SELECT * FROM shirtData WHERE product = '";
        checkIfValidSearch(sql);

    }
    //filter product size
    @FXML
    public void filterSize (ActionEvent event) {
        String sql = "SELECT * FROM shirtData WHERE size = '";
        checkIfValidSearch(sql);

    }
    //filter product color
    @FXML
    public void filterColor (ActionEvent event) {
        String sql = "SELECT * FROM shirtData WHERE color = '";
        checkIfValidSearch(sql);

    }
    //filter product price
    @FXML
    public void filterPrice (ActionEvent event) {
        String sql = "SELECT * FROM shirtData WHERE price = '";
        checkIfValidSearch(sql);
    }

    private ArrayList<String> rowCount = new ArrayList<>();//count how many items (rows) are there in the table

    //show shirt data on the page
    public void loadDataToOrderPage(String sql){
        Connection connection = DatabaseConnection.connect();
        tableProduct.getItems().clear();
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                ItemManagement itemManagement = new ItemManagement();
                itemManagement.setProduct2(rs.getString("itemId"));
                itemManagement.setProduct(rs.getString("product"));
                itemManagement.setSize(rs.getString("size"));
                itemManagement.setColor(rs.getString("color"));
                itemManagement.setPrice(rs.getInt("price"));
                itemManagement.setQuantity2(rs.getString("quantity2"));
                obList.add(itemManagement);
                rowCount.add(rs.getString("itemId"));

            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colID1.setCellValueFactory(new PropertyValueFactory<>("product2"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity2"));
        tableProduct.setItems(obList);

    }
    /**
     * Add an item into one customer's order and store each into customerInfo table.
     * If the item is currently not available, the error message will show the user that
     * the item is back-ordered. It is still added into the order but would be marked as back-ordered.
     * @param event
     */
    private void handleOrder(ActionEvent event) {
        productAvailabilityOK.setText(" ");
        productAvailability.setText(" ");
        //if user click the add items count will add one
        countOrderItem++;//count how many times user click on add items
        errorIdMsg.setText(" ");
        errorNameMsg.setText(" ");
        errorQuantityMsg.setText(" ");
        errorMonthMsg.setText(" ");
        errorSomeWhereMsg.setText(" ");

        Connection connection = DatabaseConnection.connect();
        String sql;
        try {
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();

            if(Integer.parseInt(itemIdField.getText())<1 || Integer.parseInt(itemIdField.getText())> rowCount.size()) {
                //the method allows more items to be added to the database, the upper limit grows as more items are added
                int s1 = Integer.parseInt(quantityField.getText());
                errorIdMsg.setText("Invalid Input");
                throw new Exception();
            }
            //check quantity field input is integer or string
            int s2 = Integer.parseInt(quantityField.getText());
            //check customer id field is number or string and whether it is in 1-100 range
            if(Integer.parseInt(customerNameField.getText())<1 ||
                    Integer.parseInt(customerNameField.getText())>membershipCount.size()) {
                //user could add more membership info to access in the database
                int s3 = Integer.parseInt(customerNameField.getText());
                errorNameMsg.setText("Invalid Input");

                throw new Exception();
            }
            //check month
            if(Integer.parseInt(orderMonthField.getText())<1 ||
                    Integer.parseInt(orderMonthField.getText())>12) {
                int s4 = Integer.parseInt(orderMonthField.getText());
                errorMonthMsg.setText("Invalid Input");
                throw new Exception();
            }
            //add customer name and location to customerInfo database
            sql = "INSERT INTO CustomerInfo (customerName,  quantity, product, orderCount) " +
                    "VALUES ('"+customerNameField.getText()+"', '"+
                   quantityField.getText()+"', '"+itemIdField.getText()+"', " +
                    "((SELECT MAX(orderCount) FROM CustomerInfo) +1))";
            stmt.executeUpdate(sql);

            //read price from shirtData and put into CustomerInfo
            sql="UPDATE CustomerInfo " +
                    "SET price = (SELECT price " +
                    "FROM shirtData " +
                    "WHERE itemId = CustomerInfo.product) " +
                    "WHERE EXISTS (SELECT price " +
                    "FROM shirtData " +
                    "WHERE itemId = CustomerInfo.product)";
            stmt.executeUpdate(sql);

            //calculate subTotal
           sql = "UPDATE CustomerInfo " +
                   "SET subTotal = (price * quantity)";
            stmt.executeUpdate(sql);

            //update quantity in the database
            sql="UPDATE shirtData SET quantity = CASE " +
                    "WHEN quantity-"+quantityField.getText()+" >=0 " +
                    "THEN quantity-"+quantityField.getText()+" " +
                    "ELSE '-1' END "+// -1 means back-ordered
                    "WHERE itemId = "+itemIdField.getText()+";";

            stmt.executeUpdate(sql);

            //update availability
            sql="UPDATE CustomerInfo " +
                    "SET productAvailability = " +
                    "CASE WHEN (SELECT shirtData.quantity FROM shirtData WHERE shirtData.itemId = "
                    +itemIdField.getText()+") >=0 AND " +"product = '"+itemIdField.getText()+"' "+
                    "THEN '0' " +
                    "ELSE '-1' END " +
                    "WHERE customerName = '"+customerNameField.getText()+"' AND product = '"+itemIdField.getText()+"' ;";
            stmt.executeUpdate(sql);

            //update quantity2 (label those unavailable product with back-ordered)
            sql="UPDATE shirtData " +
                    "SET quantity2 = " +
                    "CASE WHEN quantity >=0 "+
                    "THEN quantity " +
                    "ELSE 'back-ordered' END ";
            stmt.executeUpdate(sql);


            stmt.close();
            connection.commit();

            //read from edited database and prompt payment window

              ResultSet rs = connection.createStatement().executeQuery("SELECT itemId, product, size, color, " +
                      "quantity, price FROM shirtData " +
                     "WHERE itemId="+itemIdField.getText());
              while(rs.next()) {
                  if (rs.getInt("quantity") < 0) {//mark the product as back-ordered and show to user
                      if(rs.getString("price") == null){
                          throw new Exception();
                      }
                      productAvailability.setText("itemID: " + rs.getString("itemId") + "\n back-ordered");
                      productAvailabilityOK.setText("");
                  }
                  else{
                      productAvailability.setText("");
                      productAvailabilityOK.setText("OK!");
                  }

              }
              rs.close();
              stmt.close();

              connection.close();


        } catch(NumberFormatException e1){
            errorSomeWhereMsg.setText("Invalid input. Please check.");
            System.out.println("Invalid Input! 1");
            countOrderItem--;//if the item is not added successfully, the count will be deducted by one
        }
        catch (SQLException throwables) {
            errorSomeWhereMsg.setText("Invalid input. Please check.");
            System.out.println(throwables);
            countOrderItem--;//if the item is not added successfully, the count will be deducted by one

        }catch(Exception e){
            errorSomeWhereMsg.setText("Invalid input. Please check.");
            System.out.println("Invalid Input -2");
            System.out.println(e);
            countOrderItem--;//if the item is not added successfully, the count will be deducted by one
        }

    }
    //open a new window and show the finished order
    //also pass some value to the new window and show them on the screen
    public void finishOrder(ActionEvent event) {
        String customerName="";
        Connection connection = DatabaseConnection.connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM MembershipInfo " +
                    "WHERE id="+customerNameField.getText());
            while(rs.next()) {
                 customerName = rs.getString("first_name");

            }
            rs.close();
            connection.close();
            //open the OrderResult window for user input (credit card information)
        FXMLLoader fxmlLoader = new FXMLLoader(SystemApp.class.getResource("OrderResult.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 423, 650);
            CreditCardController creditCardController = fxmlLoader.getController();
            creditCardController.showInformation(customerName,"P"+customerNameField.getText()+"_"
                            +orderMonthField.getText()+(int)(Math.random()*10000000), orderMonthField.getText(),
                    customerNameField.getText(), countOrderItem);
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //reset the counter
            countOrderItem=0;
            System.out.println("countOrderItem reset = "+countOrderItem);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
