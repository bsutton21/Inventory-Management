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
public class Inventory {
    
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    // Add part
    public static void addPart(Part partAdd) {
        allParts.add(partAdd);
    }
    
    // Add product
    public static void addProduct(Product productAdd) {
        allProducts.add(productAdd);
    }
    
    // Lookup part by part ID
    public static Part lookupPart(int partIdSearch) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartId() == partIdSearch) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    // Lookup product by product ID
    public static Product lookupProduct(int productIdSearch) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductId() == productIdSearch) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    
    // Lookup part by name
    public static Part lookupPart(String partNameSearch) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartName().equals(partNameSearch)) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }
    
    // Lookup product by name
    public static Product lookupProduct(String productNameSearch) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allProducts.get(i).getProductName().equals(productNameSearch)) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    
    // Update part
    public static void updatePart(int updatePartIndex, Part part) {
        allParts.set(updatePartIndex, part);
    }
    
    // Update product
    public static void updateProduct(int updateProductIndex, Product product) {
        Inventory.deleteProduct(updateProductIndex);
        Inventory.addProduct(product);
        //allProducts.set(updateProductIndex, product);
    }
    
    // Delete part
    public boolean deletePart(int partRemove) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allParts.get(i).getPartId() == partRemove) {
                allParts.remove(i);
                break;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    // Delete product
    public static boolean deleteProduct(int productRemove) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductId() == productRemove) {
                allProducts.remove(i);
                break;
            }
            /*else {
                return false;
            }*/
        }
        return true;
    }
    
    // Getter that returns all parts in the array list
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    // Getter that returns all products in the array list
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
