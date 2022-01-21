/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bsutt
 */
public class Add_ProductController implements Initializable {

    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    private double associatedPartsPrice = 0;

    @FXML
    private TextField addProductIdText; // auto-generated -- not used
    @FXML
    private TextField addProductNameText;
    @FXML
    private TextField addProductInvText;
    @FXML
    private TextField addProductPriceText;
    @FXML
    private TextField addProductMaxText;
    @FXML
    private TextField addProductMinText;
    @FXML
    private Button addProductSearchButton;
    @FXML
    private TextField addProductSearchText;
    @FXML
    private TableView<Part> addTable;
    @FXML
    private TableView<Part> deleteTable;
    @FXML
    private Button addProductAddButton;
    @FXML
    private Button addProductDeleteButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private TableColumn<Part, Integer> addPartIdColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addPartInvColumn;
    @FXML
    private TableColumn<Part, Double> addPartPriceColumn;
    @FXML
    private TableColumn<Part, Integer> deletePartIdColumn;
    @FXML
    private TableColumn<Part, String> deletePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> deletePartInvColumn;
    @FXML
    private TableColumn<Part, Double> deletePartPriceColumn;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addTable.setItems(Inventory.getAllParts());
        addPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        deleteTable.setItems(tempAssociatedParts);
        deletePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        deletePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        deletePartInvColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        deletePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));  
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
            stage = (Stage) addProductCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
            root = loader.load();        
            Scene mainScreenScene = new Scene(root);
            stage.setScene(mainScreenScene);
            stage.show();
            Main_ScreenController controller = loader.getController();
        }
         
    }
    
    // Searches the top table for parts
    @FXML
    private void searchAssociatedParts(MouseEvent event) {
        String partSearch = addProductSearchText.getText();
        
        int partId = Integer.valueOf(partSearch);
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        searchResults.add(Inventory.lookupPart(partId));
        
        if(addProductSearchText.getText().isEmpty()) {
            addTable.setItems(Inventory.getAllParts());
        }
        else {
            addTable.setItems(searchResults);
        }
    }

    // Adds associated parts to the bottom table
    @FXML
    private void addAssociatedPart(MouseEvent event) {
        Part part = addTable.getSelectionModel().getSelectedItem();
        tempAssociatedParts.add(part);
        updateDeletePartTable();
    }
    
    // Deletes the associated part from the bottom table
    @FXML
    private void deleteAssociatedPart(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the associate part.  Do you wish to delete?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part part = deleteTable.getSelectionModel().getSelectedItem();
            tempAssociatedParts.remove(part);
        }
    }

    // Saves the changes and changes to the Main Screen scene
    @FXML
    private void saveButton(MouseEvent event) throws IOException {
        int id = Inventory.getAllProducts().size() + 1;
        String name = addProductNameText.getText();
        double price = Double.parseDouble(addProductPriceText.getText());
        int stock = Integer.parseInt(addProductInvText.getText());
        int min = Integer.parseInt(addProductMinText.getText());
        int max = Integer.parseInt(addProductMaxText.getText());
        
        if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Please ensure the stock is between the Min and Max values.");
            alert.showAndWait();
        }
        else {
            if (associatedPartsPrice < price) {
                for (Part part: tempAssociatedParts){
                    associatedPartsPrice = associatedPartsPrice + part.getPartPrice();
                }
                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(newProduct);

                for (Part part: tempAssociatedParts) {
                    newProduct.addAssociatedPart(part);
                }
                Stage stage;
                Parent root;
                stage = (Stage) addProductSaveButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
                root = loader.load();
                Scene mainScreenScene = new Scene(root);
                stage.setScene(mainScreenScene);
                stage.show();
                Main_ScreenController controller = loader.getController();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("The total price for the associated parts exceeds the price of the product");
                alert.showAndWait();
            }
            associatedPartsPrice = 0;
        }
    }
    
    public void updateAddPartTable() {
        addTable.setItems(Inventory.getAllParts());
    }
    
    public void updateDeletePartTable() {
        deleteTable.setItems(tempAssociatedParts);
    }
    
}
