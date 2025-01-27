package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private DatePicker day;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        List<Customer> customerList = DBConnection.getInstance().getDBConnection();

        if(!(txtName.getText().trim().isEmpty() && txtAddress.getText().trim().isEmpty() && txtPhoneNumber.getText().trim().isEmpty() && cmbTitle.getValue().trim().isEmpty() && day.getValue() == null)) {
            customerList.add(
                    new Customer(txtId.getText(), cmbTitle.getValue(), txtName.getText(), txtAddress.getText(), txtPhoneNumber.getText(), day.getValue())
            );
            txtId.setText("00"+generate());
            cmbTitle.setValue(null);
            txtName.setText(null);
            txtAddress.setText(null);
            txtPhoneNumber.setText(null);
            day.setValue(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.setItems(FXCollections.observableArrayList("Mr.", "Miss", "Mrs."));
        txtId.setText("00"+generate());
    }

    private static int index = 1;
    public int generate(){
        List<Customer> templist = DBConnection.getInstance().getDBConnection();
        if(templist.isEmpty()){
            return index;
        }else{
            return ++index;
        }
    }
}
