package template.try_demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The stockData class provides methods to get required stock information and set the data
 * in assigned places for users' view.
 */
public class StockData {

    private StringProperty product = new SimpleStringProperty();
    private  StringProperty size = new SimpleStringProperty();
    private  StringProperty color = new SimpleStringProperty();
    private IntegerProperty price = new SimpleIntegerProperty();
    private  StringProperty product2 = new SimpleStringProperty();
    private  StringProperty description = new SimpleStringProperty();
    private  StringProperty quantity = new SimpleStringProperty();
    private IntegerProperty sales = new SimpleIntegerProperty();
    private IntegerProperty month = new SimpleIntegerProperty();
    //identification
    public String getProduct2(){
        return product2.get();

    }
    public void setProduct2(String value){
        product2.set(value);

    }
    public StringProperty product2Property(){
        return product2;
    }

    //Product
    public String getProduct(){
        return product.get();

    }
    public void setProduct(String value){
        product.set(value);

    }
    public StringProperty productProperty(){
        return product;
    }

    //size
    public String getSize(){
        return size.get();

    }
    public void setSize(String value){
        size.set(value);

    }
    public StringProperty sizeProperty(){
        return size;
    }

    //color
    public String getColor(){
        return color.get();

    }
    public void setColor(String value){
        color.set(value);

    }
    public StringProperty colorProperty(){
        return color;
    }

    //price
    public int getPrice(){
        return price.get();
    }
    public void setPrice(int value){
        price.set(value);
    }
    public IntegerProperty priceProperty(){
        return price;
    }

    //description
    public String getDescription(){
        return description.get();

    }
    public void setDescription(String value){
        description.set(value);

    }
    public StringProperty descriptionProperty(){
        return description;
    }
    //quantity
    public String getQuantity(){
        return quantity.get();

    }
    public void setQuantity(String value){
        quantity.set(value);

    }
    public StringProperty quantityProperty(){
        return quantity;
    }

    //sales

    public int getSales(){
        return sales.get();
    }
    public void setSales(int value){
        sales.set(value);
    }
    public IntegerProperty salesProperty(){
        return sales;
    }
//month
    public int getMonth(){
        return month.get();
    }
    public void setMonth(int value){
        month.set(value);
    }
    public IntegerProperty MonthProperty(){
        return month;
    }

}

