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
import javafx.application.Platform;
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
 * @author Blake Sutton
 */
public class Main_ScreenController implements Initializable {

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partCostColumn;
    @FXML
    private Button partAdd;
    @FXML
    private Button partModify;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productCostColumn;
    @FXML
    private Button productAdd;
    @FXML
    private Button productModify;
    @FXML
    private Button partSearch;
    @FXML
    private TextField searchParts;
    @FXML
    private Button exitButton;
    @FXML
    private Button productDelete;
    @FXML
    private Button productSearch;
    @FXML
    private TextField searchProducts;
    @FXML
    private Button partDelete;
    
    private static Product modifiedProduct;
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set parts table
        partsTable.setItems(Inventory.getAllParts());
        
        //Fill parts columns with values
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        // Set products table
        productsTable.setItems(Inventory.getAllProducts());
        
        // Fill products columns with values
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }    
    

    // Delete parts from the parts table
    @FXML
    private void deletePart(MouseEvent event) {
        ObservableList<Part> selectedRows, allParts;
        allParts = partsTable.getItems();
        //specifically the selected rows
        selectedRows = partsTable.getSelectionModel().getSelectedItems();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the product.  Do you wish to delete the product?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //loop over the selected rows and delete the objects from the table
            for (Part part : selectedRows) {
                allParts.remove(part);
            }
            partsTable.setItems(Inventory.getAllParts());
        }
    }
    
    // Loopup parts in the parts table
    @FXML
    private void lookupPart(MouseEvent event) {        
        String partSearch = searchParts.getText();
        int partId = Integer.valueOf(partSearch);
        
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        searchResults.add(Inventory.lookupPart(partId));
        
        if(searchParts.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
        else {
            partsTable.setItems(searchResults);
        }
    }

    // Delete products from the products table
    @FXML
    private void deleteProduct(MouseEvent event) {
        ObservableList<Product> selectedRows, allProducts;
        allProducts = productsTable.getItems();
        //specifically the selected rows
        selectedRows = productsTable.getSelectionModel().getSelectedItems();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the product.  Do you wish to delete the product?");
        alert.setTitle("CONFIRMATION");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //loop over the selected rows and delete the objects from the table
            for (Product product : selectedRows) {
                allProducts.remove(product);
            }
            productsTable.setItems(Inventory.getAllProducts());
        }
    }
    
    // Exit the application
    @FXML
    private void exitButton(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    // Lookup products in the product table
    @FXML
    private void lookupProduct(MouseEvent event) {
        String prodSearch = searchProducts.getText();
        int productId = Integer.valueOf(prodSearch);
        
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        searchResults.add(Inventory.lookupProduct(productId));
        
        if(searchProducts.getText().isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        }
        else {
            productsTable.setItems(searchResults);
        }
    }

    // Change to the Add Part scene
    @FXML
    public void changeSceneAddPart(MouseEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) partAdd.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Add_Part.fxml"));
        root = loader.load();        
        Scene addPartScene = new Scene(root);
        stage.setScene(addPartScene);
        stage.show();
        Add_PartController controller = loader.getController();        
    }
    
    // Change to the Modify Part scene
    @FXML
    public void changeSceneModifyPart(MouseEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) partModify.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Modify_Part.fxml"));
        root = loader.load();        
        Scene modifyPartScene = new Scene(root);
        stage.setScene(modifyPartScene);
        stage.show();
        Modify_PartController controller = loader.getController(); 
        Part part = partsTable.getSelectionModel().getSelectedItem();
        Part.indexModifyPart = Inventory.getAllParts().indexOf(part);
        controller.setPart(part);
    }
    
    // Change to the Add Product scene
    @FXML
    public void changeSceneAddProduct(MouseEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) productAdd.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Add_Product.fxml"));
        root = loader.load();        
        Scene addProductScene = new Scene(root);
        stage.setScene(addProductScene);
        stage.show();
        Add_ProductController controller = loader.getController();        
    }
    
    // Change to the Modify Product scene
    @FXML
    public void changeSceneModifyProduct(MouseEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) productModify.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Modify_Product.fxml"));
        root = loader.load();        
        Scene modifyProductScene = new Scene(root);
        stage.setScene(modifyProductScene);
        stage.show();
        Modify_ProductController controller = loader.getController(); 
        Product product = productsTable.getSelectionModel().getSelectedItem();
        controller.setProduct(product);
        setModifiedProduct(product);
    }	
    
    // Called in Main_ScreenController to set the modified product
    public void setModifiedProduct(Product modifiedProduct) {
        Main_ScreenController.modifiedProduct = modifiedProduct;
    }
    
    // Called in Modify_ProductController to get the modified product
    public static Product getModifiedProduct() {
        return modifiedProduct;
    }
}