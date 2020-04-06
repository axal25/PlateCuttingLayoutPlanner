package ui.old;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class CustomObjectWithProperties {
    public final static String FIRST_NAME_PROPERTY_DEFAULT_VALUE = null;
    public final static String FIRST_NAME_PROPERTY_NAME = "firstName";

    private StringProperty firstName = null;

    public CustomObjectWithProperties() {
        CustomObjectWithProperties firstNamePropertyParent = this;
        firstName = new SimpleStringProperty(firstNamePropertyParent, FIRST_NAME_PROPERTY_NAME, FIRST_NAME_PROPERTY_DEFAULT_VALUE);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String toStringCombined() {
        return this.toString() + ": " + this.toJson();
    }

    public String toJson() {
        return "{\n\r" +
        "\t" + "firstName: {" + "\n\r" +
        "\t\t" + "name: \"" + this.firstName.getName() + "\"\n\r" +
        "\t\t" + "value: \"" + this.firstName.getValue() + "\"\n\r" +
        "\t" + "}" + "\n\r" +
        "}";
    }
}