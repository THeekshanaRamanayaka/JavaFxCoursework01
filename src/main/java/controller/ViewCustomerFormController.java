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
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    private JFXTextField address;

    @FXML
    private DatePicker day;

    @FXML
    private TableColumn<?, ?> colladdress;

    @FXML
    private TableColumn<?, ?> collcontact;

    @FXML
    private TableColumn<?, ?> colldob;

    @FXML
    private TableColumn<?, ?> collid;

    @FXML
    private TableColumn<?, ?> collname;

    @FXML
    private TableColumn<?, ?> colltitle;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField phone;

    @FXML
    private TableView<Customer> resulttable;

    @FXML
    private JFXTextField search;

    @FXML
    private JFXComboBox<String> title;

    List<Customer> templist = DBConnection.getInstance().getDBConnection();

    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        int index = check(search.getText());
        templist.remove(index);
        id.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
        title.setValue(null);
        day.setValue(null);
        search.setText("");
        view();
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        int index = check(search.getText());
        if(!(name.getText().isEmpty() && address.getText().isEmpty() && phone.getText().isEmpty() && title.getValue() == null && day.getValue() == null)){
            templist.get(index).setName(name.getText());
            templist.get(index).setTitle(String.format("%s", title.getValue()));
            templist.get(index).setAddress(address.getText());
            templist.get(index).setPhone(phone.getText());
            templist.get(index).setDay(day.getValue());

            id.setText("");
            name.setText("");
            address.setText("");
            phone.setText("");
            title.setValue(null);
            day.setValue(null);
            search.setText("");
            view();
        }
    }

    @FXML
    void mouseclicked() {
        int index = resulttable.getSelectionModel().getFocusedIndex();
        search.setText(templist.get(index).getId());
        id.setText(templist.get(index).getId());
        name.setText(templist.get(index).getName());
        title.setValue(templist.get(index).getTitle());
        address.setText(templist.get(index).getAddress());
        phone.setText(templist.get(index).getPhone());
        day.setValue(templist.get(index).getDay());
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        int index = check(search.getText());
        if(index!=-1){
            id.setText(templist.get(index).getId());
            name.setText(templist.get(index).getName());
            title.setValue(templist.get(index).getTitle());
            address.setText(templist.get(index).getAddress());
            phone.setText(templist.get(index).getPhone());
            day.setValue(templist.get(index).getDay());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setItems(FXCollections.observableArrayList("Mr.", "Miss", "Mrs."));
        id.setDisable(true);
        view();
    }

    public void view(){

        this.collid.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colltitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.collname.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.collcontact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.colldob.setCellValueFactory(new PropertyValueFactory<>("bday"));

        List<Customer> customerlist= DBConnection.getInstance().getDBConnection();
        ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();
        customerlist.forEach(customerObservableList::add);
        this.resulttable.setItems(customerObservableList);
        this.resulttable.refresh();
    }
    public int check(String search) {
        List<Customer> tempCustomerlist = DBConnection.getInstance().getDBConnection();

        for (int i = 0; i < tempCustomerlist.size(); i++) {
            if (tempCustomerlist.get(i).getId().equals(search)) {
                return i;
            }
        }
        return -1;
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        view();
    }
}
