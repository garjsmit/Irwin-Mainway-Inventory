package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {

    protected ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    protected int productID, productStock, productMin, productMax;
    protected String productName;
    protected double productPrice;

    public Product(int productId, int productStock, int productMin, int productMax, String productName, double productPrice) {
        this.productID = productID;
        this.productStock = productStock;
        this.productMin = productMin;
        this.productMax = productMax;
        this.productName = productName;
        this.productPrice = productPrice;
    }
    public void setProductID(int productID) { this.productID = productID;    }

    public void setProductStock(int productStock) { this.productStock = productStock;    }

    public void setProductMin(int productMin) {  this.productMin = productMin;   }

    public void setProductMax(int productMax) {  this.productMax = productMax;    }

    public void setProductName(String productName) {  this.productName = productName;    }

    public void setProductPrice(double productPrice) {   this.productPrice = productPrice;    }


    public ObservableList<Part> getAssociatedParts() { return associatedParts;    }

    public int getProductID() {  return productID; }

    public int getProductStock() {  return productStock; }

    public int getProductMin() {   return productMin;  }

    public int getProductMax() {   return productMax;    }

    public String getProductName() {   return productName;   }

    public double getProductPrice() {   return productPrice;    }

    public boolean deleteAssociatedPart(int partToRemove) {
        for (int i = 0; i < associatedParts.size(); i++)
            if (associatedParts.get(i).getId() == partToRemove)
                associatedParts.remove(i);
        return true;
    }

    public void addAssociatedPart(Part partToAdd){
        associatedParts.add(partToAdd);
    }

}
