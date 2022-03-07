package template.try_demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The class controls the discount catalog and allows users to search for the products they want.
 * It also provides functions for users to update the quantity of items. Users could as well add new
 * items into the database through the same window controlled by this class.
 */
public class CatalogManagementController implements Initializable {
    @FXML
    private TextField updateQuantityField;
    @FXML
    private TextField updateItemField;
    @FXML
    private TableView<ItemManagement> browseTable;
    @FXML
    private TableColumn<ItemManagement, String> colItem_b;
    @FXML
    private TableColumn<ItemManagement, String> colProduct_b;
    @FXML
    private TableColumn<ItemManagement, String> colSize_b;
    @FXML
    private TableColumn<ItemManagement, String> colColor_b;
    @FXML
    private TableColumn<ItemManagement, String> colPrice_b;
    @FXML
    private TableColumn<ItemManagement, String> colQuantity_b;
    @FXML
    private Button searchitemid;
    @FXML
    private Button searchSize;
    @FXML
    private Button searchColor;
    @FXML
    private Button searchType;
    @FXML
    private Button searchPrice;
    @FXML
    private TextField searchValue_input;
    @FXML
    private TextField updateItemId_input;
    @FXML
    private TextField updateType_input;
    @FXML
    private TextField updateQuantity_input;
    @FXML
    private TextField updateSize_input;
    @FXML
    private TextField updateColor_input;
    @FXML
    private TextField updatePrice_input;
    @FXML
    private TextField updateDescription_input;
    @FXML
    private TextField updateCost_input;
    @FXML
    private Button addNewItem;

    //count how many items are in the list now
    private ArrayList<String> countItem = new ArrayList<>();

    ObservableList<ItemManagement> obList11 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        String s = "SELECT * FROM shirtData";
        loadDataFunction(s, connection);

    }
    //add filter button to filter user input value
    //filter item ID
    @FXML
    public void filterID (ActionEvent event) {
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        String sql = "SELECT * FROM shirtData WHERE itemId = '";
        checkIfValidSearch(sql, connection);
    }
    //filter item product type
    @FXML
    public void filterType (ActionEvent event) {
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        String sql= "SELECT * FROM shirtData WHERE product = '";
        checkIfValidSearch(sql,connection);
    }
    //filter product size
    @FXML
    public void filterSize (ActionEvent event) {
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        String sql = "SELECT * FROM shirtData WHERE size = '";
        checkIfValidSearch(sql, connection);
    }
    //filter product color
    @FXML
    public void filterColor (ActionEvent event) {
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        String sql = "SELECT * FROM shirtData WHERE color = '";
        checkIfValidSearch(sql, connection);
    }

    //filter product price
    @FXML
    public void filterPrice (ActionEvent event) {
        Connection connection = DatabaseConnection.connect();
        browseTable.getItems().clear();
        String sql = "SELECT * FROM shirtData WHERE price = '";
        checkIfValidSearch(sql, connection);
    }
    @FXML
    private Label searchWarningMsg_filter;//show warning message if user choose incorrect filtering condition
    //or entering invalid value
    public void checkIfValidSearch(String sql, Connection connection){
        try{
            loadDataFunction(sql
                    + searchValue_input.getText()+"'", connection);
            searchWarningMsg_filter.setText(" ");
            if(browseTable.getItems().isEmpty()){//if cannot find data matched in database, then show error msg
                searchWarningMsg_filter.setText("Invalid Input.");
            }
        } catch (Exception e) {
            searchWarningMsg_filter.setText("Invalid Input.");
        }

    }
    //modulate the above functions
    public void loadDataFunction(String checkValue, Connection connection){

        try {
            ResultSet rs10 = connection.createStatement().executeQuery(checkValue);
            while (rs10.next()) {
                ItemManagement itemManagement = new ItemManagement();
                itemManagement.setProduct2(rs10.getString("itemId"));
                itemManagement.setProduct(rs10.getString("product"));
                itemManagement.setSize(rs10.getString("size"));
                itemManagement.setColor(rs10.getString("color"));
                itemManagement.setPrice(rs10.getInt("price"));
                itemManagement.setQuantity2(rs10.getString("quantity2"));
                obList11.add(itemManagement);
                countItem.add(rs10.getString("itemId"));
            }
        } catch (SQLException throwables) {
            System.out.println("Invalid input. Please check the type of value you entered.");
            throwables.printStackTrace();
        }

        colItem_b.setCellValueFactory(new PropertyValueFactory<>("product2"));
        colProduct_b.setCellValueFactory(new PropertyValueFactory<>("product"));
        colSize_b.setCellValueFactory(new PropertyValueFactory<>("size"));
        colColor_b.setCellValueFactory(new PropertyValueFactory<>("color"));
        colPrice_b.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity_b.setCellValueFactory(new PropertyValueFactory<>("quantity2"));
        browseTable.setItems(obList11);
    }
//update the database regarding items' quantity on hand per user's input
    @FXML
    public void updateQuantityOnHand (ActionEvent event) {
        searchWarningMsg_filter.setText(" ");
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table

        try {
            //check if the item is in the list or not
            if(Integer.parseInt(updateItemField.getText()) <1 ||
                    Integer.parseInt(updateItemField.getText())>countItem.size()){
                throw new Exception();
            }
            //check if the user input valid numbers in the quantity field
            int num = Integer.parseInt(updateQuantityField.getText());

            //if in the list and user enter number in quantity field, then execute the update
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            //update quantity on hand in database per user input (shirtData table)
            String sqlUpdate = "UPDATE shirtData SET quantity = "+updateQuantityField.getText()+" , quantity2 = " +
                    updateQuantityField.getText()+" WHERE itemId = "+updateItemField.getText();
            stmt.executeUpdate(sqlUpdate);
            stmt.close();
            connection.commit();

            //read from the database again
            ResultSet rs11 = connection.createStatement().executeQuery("SELECT * FROM shirtData ");
            while (rs11.next()) {
                ItemManagement itemManagement = new ItemManagement();
                itemManagement.setProduct2(rs11.getString("itemId"));
                itemManagement.setProduct(rs11.getString("product"));
                itemManagement.setSize(rs11.getString("size"));
                itemManagement.setColor(rs11.getString("color"));
                itemManagement.setPrice(rs11.getInt("price"));
                itemManagement.setQuantity(rs11.getInt("quantity2"));
                obList11.add(itemManagement);


            }

            colItem_b.setCellValueFactory(new PropertyValueFactory<>("product2"));
            colProduct_b.setCellValueFactory(new PropertyValueFactory<>("product"));
            colSize_b.setCellValueFactory(new PropertyValueFactory<>("size"));
            colColor_b.setCellValueFactory(new PropertyValueFactory<>("color"));
            colPrice_b.setCellValueFactory(new PropertyValueFactory<>("price"));
            colQuantity_b.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            browseTable.setItems(obList11);
            if(browseTable.getItems().isEmpty()){
                throw new SQLException();
            }
            rs11.close();
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("Invalid input. Please check the type of value you entered.");
            //throwables.printStackTrace();
            searchWarningMsg_filter.setText("Invalid input");
        }catch(Exception e){
            searchWarningMsg_filter.setText("Invalid input");
        }


    }
    @FXML
    public void addNewItems(){
        Connection connection = DatabaseConnection.connect();//connect to database
        browseTable.getItems().clear();//clean the current table
        try {
            //check if the user input valid numbers in the quantity field
            int num = Integer.parseInt(updateQuantity_input.getText());
            int price = Integer.parseInt(updatePrice_input.getText());
            int cost = Integer.parseInt(updateCost_input.getText());
            //if in the list and user enter number in quantity field, then execute the update
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            //update quantity on hand in database per user input (shirtData table)
            String sqlUpdate = "INSERT INTO shirtData(itemId, product, size, color, price, quantity, quantity2, " +
                    "description, Costs, Revenue) "+
                    "VALUES (((SELECT MAX(itemId) FROM shirtData) +1), '"+
                    updateType_input.getText()+"', '"+updateSize_input.getText()+"', '" +updateColor_input.getText()+"', '"
                    +updatePrice_input.getText()+"', '"+updateQuantity_input.getText()+"', '"+updateQuantity_input.getText()+"', '"
                    +updateDescription_input.getText()+"', '"+updateCost_input.getText()+"', '"
                    +(Integer.parseInt(updatePrice_input.getText())-Integer.parseInt(updateCost_input.getText()))+"' )";
            stmt.executeUpdate(sqlUpdate);

            stmt.close();
            connection.commit();
            String sql = "SELECT * FROM shirtData ";
            loadDataFunction(sql, connection);
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
