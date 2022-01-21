/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bsutt
 */
public class Add_PartController implements Initializable {
    @FXML
    private RadioButton inHouseRadioAdd;
    @FXML
    private RadioButton outSourceRadioAdd;
    @FXML
    private TextField idfieldadd; //auto-generated so purposefully never used
    @FXML
    private TextField namefieldadd;
    @FXML
    private TextField invfieldadd;
    @FXML
    private TextField pricefieldadd;
    @FXML
    private TextField minfieldadd;
    @FXML
    private TextField maxfieldadd;
    @FXML
    private Button cancelbuttonadd;
    @FXML
    private Button savebuttonadd;
    
    private ToggleGroup partTypeToggleGroup;
    @FXML
    private Text company_machineID;
    @FXML
    private TextField companyNameMachineIdField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTypeToggleGroup = new ToggleGroup();
        this.inHouseRadioAdd.setToggleGroup(partTypeToggleGroup);
        this.outSourceRadioAdd.setToggleGroup(partTypeToggleGroup);
        companyNameMachineIdField.setEditable(false);
    }    
    // Determines if part is InHouse or OutSourced and automatically labels the last text and text field
    @FXML
    public void radioButtonChanged() {
        if (this.partTypeToggleGroup.getSelectedToggle().equals(this.inHouseRadioAdd)) {
            company_machineID.setText("Machine ID");
            companyNameMachineIdField.setPromptText("Machine ID");
            companyNameMachineIdField.setEditable(true);
        }
        if (this.partTypeToggleGroup.getSelectedToggle().equals(this.outSourceRadioAdd)) {
            company_machineID.setText("Company Name");
            companyNameMachineIdField.setPromptText("Company Name");
            companyNameMachineIdField.setEditable(true);
        }
        
    }
    
    // Prompts with confirmation box then changes back to Main Screen scene
    @FXML
    public void cancelButton(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Leave without saving changes?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) cancelbuttonadd.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
            root = loader.load();        
            Scene mainScreenScene = new Scene(root);
            stage.setScene(mainScreenScene);
            stage.show();
            Main_ScreenController controller = loader.getController();
        }
    }
    
    // Saves the changes and changes to the Main Screen scene
    @FXML
    public void saveButton(MouseEvent event) throws IOException {
        //populating constructors based on radio button selection or lack thereof
        int id = Inventory.getAllParts().size() + 1;
        String name = namefieldadd.getText();
        double price = Double.parseDouble(pricefieldadd.getText());
        int stock = Integer.parseInt(invfieldadd.getText());
        int min = Integer.parseInt(minfieldadd.getText());
        int max = Integer.parseInt(maxfieldadd.getText());
        
        if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Please ensure the stock is between the Min and Max values.");
            alert.showAndWait();
        }
        else {
            if (inHouseRadioAdd.isSelected()) {
                int machineId = Integer.parseInt(companyNameMachineIdField.getText());
                Part part = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(part);
            }
            else if (outSourceRadioAdd.isSelected()) {
                String companyName = companyNameMachineIdField.getText();
                Part part = new OutSourced(id, name, price, stock, min, max, companyName);      
                Inventory.addPart(part);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Please select either InHouse or OutSourced and fill in the appropriate new field.");
                alert.showAndWait();
            }
        }
        
        //switching scenes
        Stage stage;
        Parent root;
        stage = (Stage) savebuttonadd.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
        root = loader.load();
        Scene mainScreenScene = new Scene(root);
        stage.setScene(mainScreenScene);
        stage.show();
        Main_ScreenController controller = loader.getController();
    }

}