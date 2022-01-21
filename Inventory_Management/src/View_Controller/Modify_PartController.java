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
public class Modify_PartController implements Initializable {
    
    private boolean isInHouse;
    int partIndex = Part.partModifyIndex();

    @FXML
    private RadioButton inhouseradiomodify;
    @FXML
    private RadioButton outsourceradiomodify;
    @FXML
    private Text companynamemodify;
    @FXML
    private TextField idfieldmodify;
    @FXML
    private TextField namefieldmodify;
    @FXML
    private TextField invfieldmodify;
    @FXML
    private TextField pricefieldmodify;
    @FXML
    private TextField minfieldmodify;
    @FXML
    private TextField companynamefieldmodify;
    @FXML
    private TextField maxfieldmodify;
    @FXML
    private Button cancelbuttonmodify;
    @FXML
    private Button savebuttonmodify;
    
    private ToggleGroup partTypeToggleGroup;
    
    Part part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTypeToggleGroup = new ToggleGroup();
        this.inhouseradiomodify.setToggleGroup(partTypeToggleGroup);
        this.outsourceradiomodify.setToggleGroup(partTypeToggleGroup);
        idfieldmodify.setEditable(false);
        namefieldmodify.setEditable(true);
        invfieldmodify.setEditable(true);
        pricefieldmodify.setEditable(true);
        minfieldmodify.setEditable(true);
        maxfieldmodify.setEditable(true);
        if (companynamefieldmodify.toString().isEmpty()) {
            companynamefieldmodify.setEditable(false);
        }
        else {
            companynamefieldmodify.setEditable(true);
        }
    }    
    
    // Changes the last text and text field if the radio button is chagned to InHouse
    @FXML
    void modifyRadioInHouse (MouseEvent event) throws IOException {
        companynamemodify.setText("Machine ID");
        companynamefieldmodify.setText("");
        companynamefieldmodify.setPromptText("Machine ID");
    }
    
    // Changes the last text and text field if the radio button is chagned to OutSourced
    @FXML
    void modifyRadioOutSourced (MouseEvent event) throws IOException {
        companynamemodify.setText("Company Name");
        companynamefieldmodify.setText("");
        companynamefieldmodify.setPromptText("Company Name");
    }
    
    // Called in Main_ScreenController to populate the text fields with the part to be modified
    public void setPart(Part part) {
        this.part = part;
        
        idfieldmodify.setText(Integer.toString(part.getPartId()));
        namefieldmodify.setText(part.getPartName());
        invfieldmodify.setText(Integer.toString(part.getPartStock()));
        pricefieldmodify.setText(Double.toString(part.getPartPrice()));
        minfieldmodify.setText(Integer.toString(part.getPartMin()));
        maxfieldmodify.setText(Integer.toString(part.getPartMax()));
        if (part instanceof InHouse) {
            companynamemodify.setText("Machine ID");
            companynamefieldmodify.setText(Integer.toString(((InHouse) part).getMachineId()));
            inhouseradiomodify.setSelected(true);
        }
        else {
            companynamemodify.setText("Company Name");
            companynamefieldmodify.setText(((OutSourced) part).getCompanyName());
            outsourceradiomodify.setSelected(true);
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
            stage = (Stage) cancelbuttonmodify.getScene().getWindow();
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
        int id = Integer.parseInt(idfieldmodify.getText());
        String name = namefieldmodify.getText();
        double price = Double.parseDouble(pricefieldmodify.getText());
        int stock = Integer.parseInt(invfieldmodify.getText());
        int min = Integer.parseInt(minfieldmodify.getText());
        int max = Integer.parseInt(maxfieldmodify.getText());
                  
        if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Please ensure the stock is between the Min and Max values.");
            alert.showAndWait();
        }
        else {
            if (inhouseradiomodify.isSelected()) {
                if (part instanceof InHouse) {
                    part.setPartId(id);
                    part.setPartName(name);
                    part.setPartPrice(price);
                    part.setPartStock(stock);
                    part.setPartMin(min);
                    part.setPartMax(max);
                    ((InHouse) part).setMachineId(Integer.parseInt(companynamefieldmodify.getText()));
                }

                else {
                    Part inHousePart = new InHouse(id, name, price, stock, min, max, Integer.parseInt(companynamefieldmodify.getText()));
                    Inventory.updatePart(partIndex, inHousePart);
                }
            }
            else {
                if (part instanceof OutSourced) {
                    part.setPartId(id);
                    part.setPartName(name);
                    part.setPartPrice(price);
                    part.setPartStock(stock);
                    part.setPartMin(min);
                    part.setPartMax(max);
                    ((OutSourced) part).setCompanyName(companynamefieldmodify.getText());
                }
                else {
                    Part outSourcedPart = new OutSourced(id, name, price, stock, min, max, companynamefieldmodify.getText());
                    Inventory.updatePart(partIndex, outSourcedPart);
                }
            }
        }
        //switching scenes
        Stage stage;
        Parent root;
        stage = (Stage) savebuttonmodify.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
        root = loader.load();
        Scene mainScreenScene = new Scene(root);
        stage.setScene(mainScreenScene);
        stage.show();
        Main_ScreenController controller = loader.getController();
    }
}
