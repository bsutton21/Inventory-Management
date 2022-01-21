/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Blake Sutton, # 001109490
 */
public abstract class Part {
    
    protected int partID;
    protected String partName;
    protected double partPrice;
    protected int partStock;
    protected int min;
    protected int max;
    
    public Part(int id, String name, double price, int stock, int min, int max) {
        
        this.partID = id;
        this.partName = name;
        this.partPrice = price;
        this.partStock = stock;
        this.min = min;
        this.max = max;
    }
    
    // Setters
    public void setPartId(int id) {
        this.partID = id;
    }
    
    public void setPartName(String name) {
        this.partName = name;
    }
    
    public void setPartPrice(double price) {
        this.partPrice = price;
    }
    
    public void setPartStock(int stock) {
        this.partStock = stock;
    }
    
    public void setPartMin(int min) {
        this.min = min;
    }
    
    public void setPartMax(int max) {
        this.max = max;
    }
    
    // Getters
    public int getPartId() {
        return this.partID;
    }
    
    public String getPartName() {
        return this.partName;
    }
    
    public double getPartPrice() {
        return this.partPrice;
    }
    
    public int getPartStock() {
        return this.partStock;
    }
    
    public int getPartMin() {
        return this.min;
    }
    
    public int getPartMax() {
        return this.max;
    }
    
    public static int indexModifyPart;
    
    public static int partModifyIndex() {
        return indexModifyPart;
    }
}