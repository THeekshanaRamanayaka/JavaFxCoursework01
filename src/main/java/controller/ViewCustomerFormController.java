package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbtitle;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> coldob;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coltitle;

    @FXML
    private DatePicker day;

    @FXML
    private TableView<Customer> resulttable;

    @FXML
    private JFXTextField txtaddress;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtphone;

    @FXML
    private JFXTextField txtsearch;

    List<Customer> customerList = DBConnection.getInstance().getDBConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbtitle.setItems(FXCollections.observableArrayList(new String[]{"Mr", "Miss", "Mrs."}));
        txtid.setDisable(true);
        view();
    }

    private void view() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("day"));

        List<Customer> customerList = DBConnection.getInstance().getDBConnection();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        customerList.forEach(obj -> {
            customerObservableList.add(obj);
        });
        resulttable.setItems(customerObservableList);
        resulttable.refresh();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int index = check(txtsearch.getText());
        customerList.remove(index);
        txtid.setText(null);
        txtname.setText(null);
        txtaddress.setText(null);
        txtphone.setText(null);
        cmbtitle.setValue(null);
        day.setValue(null);
        txtsearch.setText(null);
        view();
    }

    public int check(String search) {
        List<Customer> customerList = DBConnection.getInstance().getDBConnection();
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId().equals(search)) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        view();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        int index = check(txtsearch.getText());
        if (index != -1) {
            txtid.setText(customerList.get(index).getId());
            txtname.setText(customerList.get(index).getName());
            cmbtitle.setValue(customerList.get(index).getTitle());
            txtaddress.setText(customerList.get(index).getAddress());
            txtphone.setText(customerList.get(index).getPhone());
            day.setValue(customerList.get(index).getDay());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        int index = check(txtsearch.getText());
        if (!(txtname.getText().isEmpty() && txtaddress.getText().isEmpty() && txtphone.getText().isEmpty() && cmbtitle.getValue() == null && day.getValue() == null)) {
            customerList.get(index).setName(txtname.getText());
            customerList.get(index).setTitle(cmbtitle.getValue());
            customerList.get(index).setAddress(txtaddress.getText());
            customerList.get(index).setPhone(txtphone.getText());
            customerList.get(index).setDay(day.getValue());

            txtid.setText(null);
            txtname.setText(null);
            txtaddress.setText(null);
            txtphone.setText(null);
            cmbtitle.setValue(null);
            day.setValue(null);
            txtsearch.setText(null);
            view();
        }
    }

    @FXML
    void mouseclicked(MouseEvent event) {
        int index = resulttable.getSelectionModel().getFocusedIndex();
        txtsearch.setText(customerList.get(index).getId());
        txtid.setText(customerList.get(index).getId());
        txtname.setText(customerList.get(index).getName());
        cmbtitle.setValue(customerList.get(index).getTitle());
        txtaddress.setText(customerList.get(index).getAddress());
        txtphone.setText(customerList.get(index).getPhone());
        day.setValue(customerList.get(index).getDay());
    }
}
