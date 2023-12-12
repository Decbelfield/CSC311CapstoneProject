package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller for the help section of the application.
 * This class is responsible for initializing and displaying help instructions to the user.
 */
public class HelpController implements Initializable {
    @FXML
    private TextArea textArea;
    private String helpPrompt =
            "Add Client: \n" +
            "\tTo add a new client, follow these steps \n" +
            "\t- Click on the 'Add Client' button. \n" +
            "\t- Provide the required client information. \n" +
            "\t- Click 'Add Client' at the end of the form. \n" +
            "Remove Client: \n" +
            "\tTo remove a client, follow these steps \n" +
            "\t- You can either select a client by clicking on the \n" +
            "\t  row and marking the check box or  you can hit \n" +
                    "\t  the Mark All button. \n" +
                    "\t- By clicking remove client with the client selected,\n " +
                    "\t  the client will be removed. \n" +
            "Mark All: \n" +
            "\t- Marks all clients for deletion (wasn't able to \n" +
            "\t  get the check box to be checked when mark \n" +
                    "\t  all is called). \n" +
            "Unmark All: \n" +
            "\t- Unmarks all clients that were marked.";

    /**
     * Initializes the HelpController. This method is called after all @FXML annotated fields have been injected.
     * Sets the help instructions text in the text area.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(helpPrompt);
    }


}
