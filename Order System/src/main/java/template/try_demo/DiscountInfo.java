package template.try_demo;

import javafx.beans.property.*;

/**
 * The class provide methods to retrieve discounted products' related information and set those
 * data to assigned places
 */
public class DiscountInfo {
    private StringProperty product = new SimpleStringProperty();
    private StringProperty size = new SimpleStringProperty();
    private StringProperty availablility = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private StringProperty product3 = new SimpleStringProperty();
    private StringProperty Color = new SimpleStringProperty();


    //identification
    public String getProduct3() {
        return product3.get();

    }

    public void setProduct3(String value) {
        product3.set(value);

    }

    public StringProperty product3Property() {
        return product3;
    }

    //Product
    public String getProduct() {
        return product.get();

    }

    public void setProduct(String value) {
        product.set(value);

    }

    public StringProperty productProperty() {
        return product;
    }

    //size
    public String getSize() {
        return size.get();

    }

    public void setSize(String value) {
        size.set(value);

    }

    public StringProperty sizeProperty() {
        return size;
    }

    //color
    public String getAvailablility() {
        return availablility.get();

    }

    public void setAvailablility(String value) {
        availablility.set(value);

    }

    public StringProperty availablilityProperty() {
        return availablility;
    }

    //price
    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    //description
    public String getColor() {
        return Color.get();

    }

    public void setColor(String value) {
        Color.set(value);

    }

    public StringProperty colorProperty() {
        return Color;
    }

    //quantity
    public int getQuantity() {
        return quantity.get();

    }

    public void setQuantity(int value) {
        quantity.set(value);

    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}