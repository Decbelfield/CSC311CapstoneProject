package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpController {
    @FXML
    private TextField EnterFirstName;
    @FXML
    private TextField EnterLastName;
    @FXML
    private TextField EmailText;
    @FXML
    private TextField DOBText;
    @FXML
    private TextField ZipCodeText;
    @FXML
    private TextField PhoneNumberText;
    @FXML
    private Button ClickButton;

    @FXML
    private Text firstNameError;
    @FXML
    private Text lastNameError;
    @FXML
    private Text emailError;
    @FXML
    private Text dobError;
    @FXML
    private Text zipCodeError;
    @FXML
    private Text InvalidPhoneNumber;
    @FXML
    private Text SucessfulReg;

    @FXML
    protected void initialize() {
        // Ensure all error messages and the successful registration message are initially hidden
        firstNameError.setOpacity(0.0);
        lastNameError.setOpacity(0.0);
        emailError.setOpacity(0.0);
        dobError.setOpacity(0.0);
        zipCodeError.setOpacity(0.0);
        SucessfulReg.setOpacity(0.0);
    }
    //ensures all patterns match upon hitting the registeration button
    private boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }
    //checks to make sure if everything matches, the errors are kept at opacity 0 and the registration complete text is visible

    private void checkAllFieldsValid() {
        if (firstNameError.getOpacity() == 0.0 && lastNameError.getOpacity() == 0.0 &&
                emailError.getOpacity() == 0.0 && dobError.getOpacity() == 0.0 &&
                zipCodeError.getOpacity() == 0.0&& InvalidPhoneNumber.getOpacity() == 0.0) {

            SucessfulReg.setOpacity(1.0);
        } else {
            SucessfulReg.setOpacity(0.0);
        }
    }
    //calls all of the functions in the controller to make sure all of the patterns in each function match when clicking the register button
//if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void ClickButton() {
        // Check each field when the button is clicked
        CheckFirstName();
        CheckLastName();
        CheckEmail();
        CheckDOB();
        EnterZipCode();
        CheckPhoneNumber();

        // After all checks, see if everything is valid
        checkAllFieldsValid();
    }
    //checks to make sure the first name has the correct pattern. it covers both lower case and upper case letters for words whos letters range from 2 to 25
    //if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void CheckFirstName() {
        if(!isValid(EnterFirstName.getText(), "[A-Za-z]{2,25}")){
            firstNameError.setOpacity(1.0);
        } else {
            firstNameError.setOpacity(0.0);
        }
    }
    //checks to make sure the last name has the correct pattern. it covers both lower case and upper case letters for words whos letters range from 2 to 25
    //if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void CheckLastName() {
        if(!isValid(EnterLastName.getText(), "[A-Za-z]{2,25}")){
            lastNameError.setOpacity(1.0);
        } else {
            lastNameError.setOpacity(0.0);
        }
    }
    //checks to make sure the the email has the correct pattern. it covers both upper/lower case words, numbers from 0-9 followed by any special characters like %+-
    //also checks to make sure it only accepts emails from the @farmingdale.edu
    //if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void CheckEmail() {
        if(!isValid(EmailText.getText(), "[a-zA-Z0-9._%+-]+@farmingdale\\.edu")){
            emailError.setOpacity(1.0);
        } else {
            emailError.setOpacity(0.0);
        }
    }
    //checks to make sure the date of birth is valid and the user has to make sure to include a slash for month/day/year
    //if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void CheckDOB() {
        if(!isValid(DOBText.getText(), "\\d{2}/\\d{2}/\\d{4}")){
            dobError.setOpacity(1.0);
        } else {
            dobError.setOpacity(0.0);
        }
    }
    //checks to make sure the zipcode follows the same pattern within the 5 number parameter
//if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void EnterZipCode() {
        if(!isValid(ZipCodeText.getText(), "\\d{5}")){
            zipCodeError.setOpacity(1.0);
        } else {
            zipCodeError.setOpacity(0.0);
        }
    }
    //checks to make sure the phone number follows the same pattern as intended 3 numbers for the area code followed by a dash, 3 numbers for the middle number followed by another slash and then finally the last 4 digits
    //if the pattern fails, the error text will lose its opacity and be visible
    @FXML
    protected void CheckPhoneNumber(){
        if(!isValid(PhoneNumberText.getText(), "\\d{3}-\\d{3}-\\d{4}")){
            InvalidPhoneNumber.setOpacity(1.0);
        }
        else{
            InvalidPhoneNumber.setOpacity(0.0);
        }
    }
}
