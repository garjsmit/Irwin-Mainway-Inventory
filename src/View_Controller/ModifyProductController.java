package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.beans.binding.ObjectExpression;
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

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    Product product;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField productIdTxt, productNameTxt,productStockTxt, productMaxTxt, productMinTxt, productPriceTxt;

    @FXML
    private TableView<Part> partsTable, associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol, partId2Col, partStockCol, partStock2Col;
    @FXML
    private TableColumn<Part, String> partNameCol, partName2Col;
    @FXML
    private TableColumn<Part, Double> partPriceCol, partPrice2Col;

    @FXML
    private TextField partSearchText;

    @FXML
    private Label partNotFound;

    /** Part Search Event Handler. Searches by name first, and then number.
     *@param event Search button click
     * */
    @FXML
    void onActionSearch(ActionEvent event) {

        partNotFound.setText("");
        if(!Inventory.getFilteredParts().isEmpty()) Inventory.getFilteredParts().clear(); //if getFilteredParts has items in it, clear them

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
    void onActionAddPartToProduct(ActionEvent event) {

        associatedParts.add(partsTable.getSelectionModel().getSelectedItem());

        associatedPartsTable.refresh();

    }

    /**removes the part selected in the associated parts table
     * @param event Remove associated part button click
     * */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

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

    /**Save Product button handler. Saves updated Product @ the same index.
     * Then updates the Product's associatedParts list.
     * @param event Save button click
     * */
    @FXML
    void onActionUpdateProduct(ActionEvent event) throws IOException {
        try {
            int productId = Integer.parseInt(productIdTxt.getText());
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
                Product productToUpdate = new Product(productId, productStock, productMin, productMax, productName, productPrice);
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(product), productToUpdate);
                productToUpdate.addAllAssociatedParts(associatedParts);
                newScreen(event, "/View_Controller/MainScreen.fxml");
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid text or numbers in fields.");
            alert.showAndWait();
        }
    }

    /**Initializes all the fields with values from the product selected on the Main Screen. */
    public void setProduct(Product product){
        this.product = product;

        productIdTxt.setText((String.valueOf(product.getProductId())));
        productNameTxt.setText(product.getProductName());
        productStockTxt.setText((String.valueOf(product.getProductStock())));
        productMaxTxt.setText((String.valueOf(product.getProductMax())));
        productMinTxt.setText((String.valueOf(product.getProductMin())));
        productPriceTxt.setText((String.valueOf(product.getProductPrice())));

        associatedParts = product.getAssociatedParts();
        associatedPartsTable.setItems(associatedParts);

        partId2Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock2Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice2Col.setCellValueFactory(new PropertyValueFactory<>("price"));


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

    /**Initializes the allParts tables with the ObservableLists of Parts, and the associatedParts table with the associated parts of a product.
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        //Product product = new Product(0, 0,0,0,"Product", 0.0);

        partsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems((associatedParts));

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
