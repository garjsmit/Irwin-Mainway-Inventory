package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    Product newProduct;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField productNameTxt, productStockTxt, productPriceTxt, productMinTxt, productMaxTxt, partSearchText;

    @FXML
    private TableView<Part> partsTable, associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol, partStockCol, partId2Col, partStock2Col;
    @FXML
    private TableColumn<Part, String> partNameCol, partName2Col;
    @FXML
    private TableColumn<Part, Double> partPriceCol, partPrice2Col;

    @FXML
    private Label partNotFound, pleaseSelectPart;

    /** Part Search Event Handler. Searches by name first, and then number.
    *@param event Search button click
     * */
    @FXML
    void onActionSearch(ActionEvent event) {
        partNotFound.setText("");
        if(!Inventory.getFilteredParts().isEmpty()) {
            Inventory.getFilteredParts().clear(); //if getFilteredParts has items in it, clear them
        }

        try {
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() == Integer.parseInt(partSearchText.getText())) //Then searches part number
                    Inventory.getFilteredParts().add(part);
            }
        }
        catch(NumberFormatException e){
            for (Part part : Inventory.getAllParts()) {
                if (part.getName().contains(partSearchText.getText())) //checks first for Part name
                    Inventory.getFilteredParts().add(part);
            }
        }

        if(Inventory.getFilteredParts().isEmpty()) partNotFound.setText("Part Not Found");

        partsTable.setItems(Inventory.getFilteredParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**adds the part selected in the parts table to the associated parts table.
     * @param event Add part button click
     * */
    @FXML
    void onActionAddPartToProduct(ActionEvent event){

        if(partsTable.getSelectionModel().getSelectedItem() == null) pleaseSelectPart.setText("Please select part");
        Part partToAdd = partsTable.getSelectionModel().getSelectedItem();
        if (partToAdd == null) return;

        associatedParts.add(partToAdd);

        associatedPartsTable.refresh();

    }

    /**removes the part selected in the associated parts table
     * @param event Remove associated part button click
     * */
    @FXML
    void onActionRemoveAssociatedPartFromProduct(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove associated part?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
        }

        associatedPartsTable.refresh();

    }

    /**Exit button handler
     * @param event exit button clicked
     * */
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/MainScreen.fxml");
    }

    /**Save Product button handler. Inventory must be within min and max, and max must be smaller than min, else there's an error. Also generates an error dialog if input type generates an Error.
     * Then updates the Product's associatedParts list.
     * @param event Save button click
     * */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int productId = Inventory.productIdGenerator();
            String productName = productNameTxt.getText();
            int productStock = Integer.parseInt(productStockTxt.getText());
            double productPrice = Double.parseDouble(productPriceTxt.getText());
            int productMin = Integer.parseInt(productMinTxt.getText());
            int productMax = Integer.parseInt(productMaxTxt.getText());
            if (productMax <= productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product stock max must be larger than min.");
                alert.showAndWait();
            } else if (productStock > productMax || productStock < productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product inventory must be between min and max.");
                alert.showAndWait();
            } else {
                Product newProduct = new Product(productId, productStock, productMin, productMax, productName, productPrice);
                Inventory.addProduct(newProduct);
                newProduct.addAllAssociatedParts(associatedParts);
                newScreen(event, "/View_Controller/MainScreen.fxml");
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid text or numbers in fields.");
            alert.showAndWait();
        }

    }

    /**Exit button event handler. Asks users to confirm before closing.
     * @param event Exit button click
     * */
    public void newScreen(ActionEvent event, String pathToScene) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(pathToScene));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Initializes the allParts tables with the ObservableLists of Parts.
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        newProduct = new Product(0, 0,0,0,"Product", 0.0);

        partsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(associatedParts);

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partId2Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock2Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice2Col.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
