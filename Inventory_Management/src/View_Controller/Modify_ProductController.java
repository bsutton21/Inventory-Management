/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import static View_Controller.Main_ScreenController.getModifiedProduct;
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
public class Modify_ProductController implements Initializable {

    private ObservableList<Part> currentAssociatedParts = FXCollections.observableArrayList();
    Product product;
    private int productIndex = Product.productModifyIndex();
    private int productId;
    private final Product modifiedProduct;
    private double associatedPartsPrice = 0;
    
    @FXML
    private TextField modifyProductIdText;
    @FXML
    private TextField modifyProductNameText;
    @FXML
    private TextField modifyProductInvText;
    @FXML
    private TextField modifyProductPriceText;
    @FXML
    private TextField modifyProductMaxText;
    @FXML
    private TextField modifyProductMinText;
    @FXML
    private TableView<Part> modifyProductAddTable;
    @FXML
    private TableView<Part> modifyProductDeleteTable;
    @FXML
    private Button modifyProductCancelButton;
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
    @FXML
    private Button modifyProductAddButton;
    @FXML
    private Button modifyProductDeleteButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private TextField modifyProductSearchText;

    public Modify_ProductController() {
        this.modifiedProduct = getModifiedProduct();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Product prod = Inventory.getAllProducts().get(productIndex);
        /*productId = Inventory.getAllProducts().get(productIndex).getProductId();
        modifyProductIdText.setEditable(false);
        modifyProductNameText.setEditable(true);
        modifyProductInvText.setEditable(true);
        modifyProductPriceText.setEditable(true);
        modifyProductMinText.setEditable(true);
        modifyProductMaxText.setEditable(true);
        */
        modifyProductAddTable.setItems(Inventory.getAllParts());
        addPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        // currentAssociatedParts = prod.getAllAssociatedParts();
        // modifyProductDeleteTable.setItems(prod.getAllAssociatedParts());
        deletePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        deletePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        deletePartInvColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        deletePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));  
    }    
    
    // Called in Main_ScreenController to populate the text fields with the part to be modified
    // Also populates the bottom table with the currently associated parts
    public void setProduct(Product product) {
        this.product = product;
        
        modifyProductIdText.setText(Integer.toString(product.getProductId()));
        modifyProductNameText.setText(product.getProductName());
        modifyProductInvText.setText(Integer.toString(product.getProductStock()));
        modifyProductPriceText.setText(Double.toString(product.getProductPrice()));
        modifyProductMinText.setText(Integer.toString(product.getProductMin()));
        modifyProductMaxText.setText(Integer.toString(product.getProductMax()));
        modifyProductDeleteTable.setItems(product.getAllAssociatedParts());
    }
    
    /*public void setAssociatedParts(Product product) {
        this.currentAssociatedParts = product.getAllAssociatedParts();
    }*/
    
    // Prompts with confirmation box then changes back to Main Screen scene
    @FXML
    public void cancelButton(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Leave without saving changes?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) modifyProductCancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Main_Screen.fxml"));
            root = loader.load();        
            Scene mainScreenScene = new Scene(root);
            stage.setScene(mainScreenScene);
            stage.show();
            Main_ScreenController controller = loader.getController();
        }
    }

    @FXML
    private void searchAssociatedParts(MouseEvent event) {
        String partSearch = modifyProductSearchText.getText();
        
        int partId = Integer.valueOf(partSearch);
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        searchResults.add(Inventory.lookupPart(partId));
        
        if(modifyProductSearchText.getText().isEmpty()) {
            modifyProductAddTable.setItems(Inventory.getAllParts());
        }
        else {
            modifyProductAddTable.setItems(searchResults);
        }
    }

    // Adds associated parts to the bottom table
    @FXML
    private void addAssociatedPart(MouseEvent event) {
        Part part = modifyProductAddTable.getSelectionModel().getSelectedItem();
        currentAssociatedParts.add(part);
        updateDeletePartTable();
    }

    // Deletes the associated part from the bottom table
    @FXML
    private void deleteAssociatedPart(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the associated part.  Do you wish to delete?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part part = modifyProductDeleteTable.getSelectionModel().getSelectedItem();
            currentAssociatedParts.remove(part);
        }
    }
    
    // Saves the changes and changes to the Main Screen scene
    @FXML
    public void saveModifiedProduct(MouseEvent event) throws IOException {
        int id = Integer.parseInt(modifyProductIdText.getText());
        String name = modifyProductNameText.getText();
        double price = Double.parseDouble(modifyProductPriceText.getText());
        int stock = Integer.parseInt(modifyProductInvText.getText());
        int min = Integer.parseInt(modifyProductMinText.getText());
        int max = Integer.parseInt(modifyProductMaxText.getText());
        
        if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Please ensure the stock is between the Min and Max values.");
            alert.showAndWait();
        }
        else {
            if (associatedPartsPrice < price) {
                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.updateProduct(Integer.parseInt(modifyProductIdText.getText()), newProduct);

                modifiedProduct.purgeAssociatedParts();

                for (Part part: currentAssociatedParts) {
                    newProduct.addAssociatedPart(part);
                }
                
                Stage stage;
                Parent root;
                stage = (Stage) modifyProductSaveButton.getScene().getWindow();
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
        modifyProductAddTable.setItems(Inventory.getAllParts());
    }
    
    public void updateDeletePartTable() {
        modifyProductDeleteTable.setItems(currentAssociatedParts);
    }
}