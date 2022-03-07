package template.try_demo;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * The class provides methods to get and set several properties of different items
 */
public class ItemManagement {

    private  StringProperty product = new SimpleStringProperty();
    private  StringProperty size = new SimpleStringProperty();
    private  StringProperty color = new SimpleStringProperty();
    private  IntegerProperty price = new SimpleIntegerProperty();
    private  IntegerProperty quantity = new SimpleIntegerProperty();
    private  StringProperty quantity2 = new SimpleStringProperty();
    private  StringProperty product2 = new SimpleStringProperty();
    private  StringProperty description = new SimpleStringProperty();


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
    public int getQuantity(){
        return quantity.get();

    }
        public void setQuantity(int value){
        quantity.set(value);

    }
    public IntegerProperty quantityProperty(){
        return quantity;
    }
    //quantity2
    public String getQuantity2(){
        return quantity2.get();

    }
    public void setQuantity2(String value){
        quantity2.set(value);

    }
    public StringProperty quantity2Property(){
        return quantity2;
    }
}

