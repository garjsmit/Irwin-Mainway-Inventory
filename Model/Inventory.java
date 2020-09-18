package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**Includes ObservableLists of allParts and allProducts. */
public class Inventory {


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * @return Observablelist of all parts in inventory
     * */
    public static ObservableList<Part> getAllParts(){ return allParts; }

    /**
     * @return Observablelist of filtered products. Used for Search functionality.
     * */
    public static ObservableList<Part> getFilteredParts(){ return filteredParts; }

    /**
     * @return Observablelist of all products in inventory.
     * */
    public static ObservableList<Product> getAllProducts(){ return allProducts;  }

    /**
     * @return Observablelist of filtered products. Used for search funtionality.
     * */
    public static ObservableList<Product> getFilteredProducts(){ return filteredProducts;  }


    /**
     * @param newPart Part that will be added to Inventory
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * @param newProduct Product that will be added to allProducts in Inventory
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partIDToLookup Part id that will be found
     * */
    public static Part lookupPart(int partIDToLookup){
        for(int i=0; i<allParts.size();i++) {
            if (allParts.get(i).getId() == partIDToLookup) {
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * @param partNameToLookup Part Name that will be found
     * */
    public static Part loopkupPart(String partNameToLookup){
        for(int i=0; i<allParts.size();i++) {
            if (allParts.get(i).getName() == partNameToLookup) {
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * @param productIDToLookup product ID that will be found
     * */
    public static Product lookupProduct(int productIDToLookup){
        for(int i=0; i<allProducts.size();i++) {
            if (allProducts.get(i).getProductId() == productIDToLookup) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * @param productNameToLookup Product Name that will be found
     * */
    public static Product lookupProduct(String productNameToLookup){
        for(int i=0; i<allProducts.size();i++) {
            if (allProducts.get(i).getProductName() == productNameToLookup) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * @param index Index of the part that is being modified
     * @param partToUpdate New values that will be updated for the part
     * */
    public static void updateInHouse(int index, Part partToUpdate){
        allParts.set(index, partToUpdate);
    }

    /**
     * @param index Index of the part that is being modified
     * @param partToUpdate New values that will be updated for the part
     * */
    public static void updateOutsource(int index, Part partToUpdate){
        allParts.set(index, partToUpdate);
    }


    /**
     * @param index Index of the product that is being modified
     * @param productToUpdate New values that will be updated for the product
     * */
    public static void updateProduct(int index, Product productToUpdate){
        allProducts.set(index, productToUpdate);
    }

    /**
     * @param partToDelete Part that will deleted.
     * @returns true if the part is deleted. False is it was not deleted.
     * */
    public static boolean deletePart(Part partToDelete){
        boolean removed = false;
        for(int i = 0;i<allParts.size(); i++) {
            if (allParts.get(i).getId() == partToDelete.getId()) {
                allParts.remove(i);
                removed = true;
            }
        }
        return removed;
    }

    /**
     * @param productToDelete Product that will deleted.
     * @returns true if the product is deleted. False is it was not deleted.
     * */
    public static boolean deleteProduct(Product productToDelete) {
        boolean removed = false;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductId() == productToDelete.getProductId()) {
                allProducts.remove(i);
                removed = true;
            }
        }
        return removed;
    }

    /**
     * @return Returns a randomly generated, unique part ID. Not persistent through each running of the application, but consistent throughout each screen when the app is launched.
     * */
    public static int partIdGenerator() {
        int id = 0;
        boolean taken = false;

        //Checks if the id is taken. TODO - sort the ids and optimize this search
        do {
            id = (int)(Math.random() * (9999 - 1000 + 1) + 1000);
            for (Part part : Inventory.getAllParts()) {
                if(part.getId() == id) {
                    taken = true;
                }
            }
        }
        while (taken);
        return id;
    }

    /**
     * @return Returns a randomly generated, unique product ID. Not persistent through each running of the application, but consistent throughout each screen when the app is launched.
     * */
    public static int productIdGenerator() {
        int id = 0;
        boolean taken = false;

        //Checks if the id is taken. TODO - sort the ids and optimize this search
        do {
            id = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
            for (Product product : Inventory.getAllProducts()) {
                if (product.getProductId() == id) {
                    taken = true;
                }
            }
        }
        while (taken);
        return id;
    }

}
