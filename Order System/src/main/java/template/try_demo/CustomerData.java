package template.try_demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerData {
    private StringProperty customerName = new SimpleStringProperty();
    private StringProperty order = new SimpleStringProperty();
    //name
    public String getName(){
        return customerName.get();

    }
    public void setCustomerName(String value){
        customerName.set(value);

    }
    public StringProperty customerNameProperty(){
        return customerName;
    }

    //identification
    public String getOrder(){
        return order.get();

    }
    public void setOrder(String value){
        order.set(value);

    }
    public StringProperty orderProperty(){
        return order;
    }

}
