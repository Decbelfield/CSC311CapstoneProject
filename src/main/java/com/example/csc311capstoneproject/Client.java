package com.example.csc311capstoneproject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class Client will store information pertaining to a client
 */
public class Client {

    private SimpleStringProperty fullName, firstName, lastName, middleInitial,Ssn, telephone, email, address, city, state, zip;
    private SimpleBooleanProperty marked;

    public Client() {
        firstName = null;
        middleInitial = null;
        lastName = null;
        Ssn = null;
        telephone = null;
        email = null;
        address = null;
        city = null;
        state = null;
        zip = null;
        marked = new SimpleBooleanProperty(false);
    }

    public Client(String firstName, String middleInitial, String lastName, String Ssn, String telephone, String email, String address, String city, String state, String zip) {
        this.firstName = new SimpleStringProperty(firstName);
        this.middleInitial = new SimpleStringProperty(middleInitial);
        this.lastName = new SimpleStringProperty(lastName);
        this.fullName = new SimpleStringProperty(firstName + " " + middleInitial + " " + lastName);
        this.Ssn = new SimpleStringProperty(Ssn);
        this.telephone = new SimpleStringProperty(telephone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zip = new SimpleStringProperty(zip);
        this.marked = new SimpleBooleanProperty(false);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleInitial() {
        return middleInitial.get();
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial.set(middleInitial);
    }

    public String getFullName() { return fullName.get();}

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getSsn() {
        return Ssn.get();
    }

    public void setSsn(String ssn) {
        this.Ssn.set(ssn);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCity() {
        return city.get();
    }


    public void setCity(String city) { this.city.set(city);}

    public String getState() { return state.get();}

    public void setState(String state) {
        this.state.set(state);
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public boolean isMarked() { return marked.get();}

    public SimpleBooleanProperty markedProperty() { return marked;}

    public void markClient() {
        this.marked.set(true);
    }

    public void unmarkClient() {
        this.marked.set(false);
    }

    public void setMarked(boolean marked) {
        this.markedProperty().set(marked);
    }

}