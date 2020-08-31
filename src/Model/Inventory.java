package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Inventory {

    //Declare fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts(){ return allParts; }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partIDToLookup){
        for(int i=0; i<allParts.size();i++) {
            if (allParts.get(i).getId() == partIDToLookup) {
                return allParts.get(i);
            }
        }
        return null;
    }

    public static Part loopkupPart(String partNameToLookup){
        for(int i=0; i<allParts.size();i++) {
            if (allParts.get(i).getName() == partNameToLookup) {
                return allParts.get(i);
            }
        }
        return null;
    }

    public static Product lookupProduct(int productIDToLookup){
        for(int i=0; i<allProducts.size();i++) {
            if (allProducts.get(i).getProductID() == productIDToLookup) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    public static Product lookupProduct(String productNameToLookup){
        for(int i=0; i<allProducts.size();i++) {
            if (allProducts.get(i).getProductName() == productNameToLookup) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    public static void updatePart(int index, Part partToUpdate){
        allParts.set(index, partToUpdate);
    }

    public static void updateProduct(int index, Product productToUpdate){
        allProducts.set(index, productToUpdate);
    }

    public static boolean deletePart(Part partToDelete){
        for(int i = 0;i<allParts.size(); i++){
            if (allParts.get(i).getId() == partToDelete.getId())
                allParts.remove(i);
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Product productToDelete){
        for(int i = 0;i<allProducts.size(); i++){
            if (allProducts.get(i).productID == productToDelete.getProductID())
                allProducts.remove(i);
            return true;
        }
        return false;
    }

}
