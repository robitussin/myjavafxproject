import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {
    
    @FXML
    Label homelabel;

    @FXML
    TextField changepasswordtextfield;

    @FXML
    Button changepasswordbutton;

    public void displayName(String username){

        homelabel.setText(username);
    }

    public void changePasswordHandler(ActionEvent event) throws IOException{

        String pword = changepasswordtextfield.getText();
        String uname = homelabel.getText();

        if (DatabaseHandler.changePassword(uname, pword)) {

            System.out.println("Change password successful :( ");
        }
        else
        {
            System.out.println("Change password unsuccessful :( ");
        }
    }
}
