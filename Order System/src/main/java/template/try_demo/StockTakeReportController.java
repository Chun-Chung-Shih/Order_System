package template.try_demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The class shows each item with description and quantity on hand in a stocktake report window
 */
public class StockTakeReportController implements Initializable {
    @FXML
    private TableView<ItemManagement> StockTable;
    @FXML
    private TableColumn<ItemManagement, String> colItem;
    @FXML
    private TableColumn<ItemManagement, String> colProduct;
    @FXML
    private TableColumn<ItemManagement, String> colDescription;
    @FXML
    private TableColumn<ItemManagement, String> colQuantity;

    ObservableList<ItemManagement> obList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection connection = DatabaseConnection.connect();

        try {
            //read from database
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM shirtData");
            while (rs.next()) {
                ItemManagement itemManagement = new ItemManagement();
                itemManagement.setProduct2(rs.getString("itemId"));
                itemManagement.setProduct(rs.getString("product"));
                itemManagement.setDescription(rs.getString("description"));
                itemManagement.setQuantity2(rs.getString("quantity2"));
                obList.add(itemManagement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colItem.setCellValueFactory(new PropertyValueFactory<>("product2"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity2"));
        StockTable.setItems(obList);
    }

}
