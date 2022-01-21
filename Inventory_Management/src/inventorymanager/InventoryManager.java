/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Product;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bsutt
 */
public class InventoryManager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv);
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/View_Controller/Main_Screen.fxml"));

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    void addTestData(Inventory inv) {
        //Add Test InHouse Parts
        inv.addPart(new InHouse(1, "Part I1", 3.95, 15, 5, 100, 101));
        inv.addPart(new InHouse(2, "Part I2", 2.99, 10, 10, 100, 102));
        
        //Add Test Outsourced Parts
        inv.addPart(new OutSourced(6, "Part O1", 1.99, 15, 5, 100, "ABC Co."));
        inv.addPart(new OutSourced(7, "Part O2", 2.99, 5, 5, 100, "XYZ Inc."));
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
