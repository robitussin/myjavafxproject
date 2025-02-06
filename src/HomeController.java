import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Templates;

public class HomeController implements Initializable{

    ObservableList<User> mylist = FXCollections.observableArrayList();

    @FXML
    private Button btncreate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<User, String> usernamecol;

    @FXML
    private TableColumn<User, String> passwordcol;

    @FXML
    private TableColumn<User, String> statuscol;

    @FXML
    private TableColumn<User, String> accountcreatedcol;

    @FXML
    private TableView<User> mytable;

    @FXML
    Label homelabel;

    @FXML
    TextField usernametextfield;

    @FXML
    TextField passwordtextfield;

    @FXML
    TextField statustextfield;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        loadData();
    }

    private void initializeCol(){

        usernamecol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcol.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedcol.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
        statuscol.setCellValueFactory(new PropertyValueFactory<>("accountstatus"));
    }

    private void loadData(){

        mylist.clear();

        ResultSet result = DatabaseHandler.displayusers();

        try {
            while (result.next())
            {
                String username = result.getString("Username");
                String password = result.getString("Password");
                String accountcreated = result.getString("AccountCreated");
                String accountstatus = result.getString("AccountStatus");

                mylist.add(new User(username, password, accountcreated, accountstatus));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        mytable.setItems(mylist);
    }

    @FXML
    private boolean createuser(ActionEvent event) {

        String username = usernametextfield.getText();

        String password = passwordtextfield.getText();

        String status = statustextfield.getText();

        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username provided");
            return false;
        }

        if(password.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no password provided");
            return false;
        }

        Account account = new Account(username, password, "", status);

        if(DatabaseHandler.addAccount(account))
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no successful");
            loadData();
            return true;
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("unsuccessful");
            return false;
        }
    }
    
    @FXML
    public boolean deleteuser(ActionEvent event) {

        User user = mytable.getSelectionModel().getSelectedItem();

        String username = (user.getUsername());

        System.out.println(username);

        if(DatabaseHandler.deleteUser(user))
        {
            System.out.println("User has been deleted!");
            loadData();
            return true;
        }
        else
        {
            System.out.println("User has not been deleted!");
            return false;
        }
    }
    
    @FXML
    public boolean updateuser(ActionEvent event) {

        String username = usernametextfield.getText();

        String password = passwordtextfield.getText();

        String status = statustextfield.getText();

        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            System.out.println("No username!");
            return false;
        }

        if(password.length() == 0)
        {
            System.out.println("No password!");
            return false;
        }

        Account account = new Account(username, password, "", status);

        if(DatabaseHandler.updateUser(account))
        {
            System.out.println("Successful");
            loadData();
            return true;
        }
        else
        {
            System.out.println("unsuccessful");
            return false;
        }
    }
}
