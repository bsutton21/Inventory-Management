/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Blake Sutton, # 001109490
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productId;
    private String productName;
    private double productPrice;
    private int productStock;
    private int min;
    private int max;
    public static int indexModifyProduct;
    
    public Product(int id, String name, double price, int stock, int min, int max) {
        
        this.productId = id;
        this.productName = name;
        this.productPrice = price;
        this.productStock = stock;
        this.min = min;
        this.max = max;
    }
    
    // Setters
    public void setProductId(int id) {
        this.productId = id;
    }
    
    public void setProductName(String name) {
        this.productName = name;
    }
    
    public void setProductPrice(double price) {
        this.productPrice = price;
    }
    
    public void setProductInStock(int stock) {
        this.productStock = stock;
    }
    
    public void setProductMin(int min) {
        this.min = min;
    }
    
    public void setProductMax(int max) {
        this.max = max;
    }
    
    // Getters
    public int getProductId() {
        return this.productId;
    }
    
    public String getProductName() {
        return this.productName;
    }
    
    public double getProductPrice() {
        return this.productPrice;
    }
    
    public int getProductStock() {
        return this.productStock;
    }
    
    public int getProductMin() {
        return this.min;
    }
    
    public int getProductMax() {
        return this.max;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    
    public void purgeAssociatedParts() {
        associatedParts = FXCollections.observableArrayList();
    }
            
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    public static int productModifyIndex() {
        return indexModifyProduct;
    }
}
