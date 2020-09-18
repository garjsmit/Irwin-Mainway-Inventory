package View_Controller;

        import Model.Inventory;
        import Model.Part;
        import Model.Product;
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
        import static Model.Inventory.deletePart;
        import static Model.Inventory.deleteProduct;

/**Main Screen Controller. First screen that opens.*/
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol, partStockCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Product, Integer> productIDCol, productStockCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField partSearchText, productSearchText;

    @FXML
    private Label partNotFound, productNotFound, selectProduct, selectPart;

    /** Part Search Event Handler. Searches by name first, and then number.
     *@param event Search button click
     * */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        partNotFound.setText("");

        if(!Inventory.getFilteredParts().isEmpty()) {
            Inventory.getFilteredParts().clear(); //if getFilteredParts has items in it, clear them
        }

        // parseInt() throws an exception if the user enters a String
        // catch and the partSearchText is used as an int to search for partIds
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

    /**Product Search Event Handler. Searches first by name and then by id.
     * @param event Product search button click
     * */
    @FXML
    void onActionProductSearch(ActionEvent event) {

        productNotFound.setText("");
        if(!Inventory.getFilteredProducts().isEmpty()) Inventory.getFilteredProducts().clear();

        try {
            for (Product product : Inventory.getAllProducts()) {
                if (product.getProductId() == Integer.parseInt(productSearchText.getText())) //Then searches part number
                    Inventory.getFilteredProducts().add(product);
            }
        }
        catch(NumberFormatException e){
            for (Product product: Inventory.getAllProducts()) {
                if (product.getProductName().contains(productSearchText.getText())) //checks first for Part name
                    Inventory.getFilteredProducts().add(product);
            }
        }

        if(Inventory.getFilteredProducts().isEmpty()) productNotFound.setText("Product Not Found");

        productTable.setItems(Inventory.getFilteredProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }

    /**Add Part button handler. Opens AddPartController
     * @param event Add Part button click
     * */
    @FXML
    void onActionAddPartScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/AddPart.fxml");

    }

    /**Add Product button handler. Opens AddProductController
     * @param event Add Product button click
     * */
    @FXML
    void onActionAddProductScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/AddProduct.fxml");
    }

    /**Modify Part button handler. Opens ModifyPartController
     * @param event Modify Product button click
     * */
    @FXML
    void onActionModifyPartScreen(ActionEvent event) throws IOException {

        if(partsTable.getSelectionModel().getSelectedItem() == null) {
            selectPart.setText("Please select part to modify.");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/ModifyPart.fxml"));
        loader.load();

        ModifyPartController ModPartController = loader.getController();
        ModPartController.setPart(partsTable .getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Modify Product button handler. Opens ModifyProductController
     * @param event Modify Product button click
     * */
    @FXML
    void onActionModifyProductScreen(ActionEvent event) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() == null) {
            selectProduct.setText("Please select product to modify.");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController ModProdController = loader.getController();
        ModProdController.setProduct(productTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Delete Part button handler. Asks user to confirm before deleting. Informational dialog box informing user part was deleted.
     * @param event Delete Part button click
     * */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        if(partsTable.getSelectionModel().getSelectedItem() == null){
            selectPart.setText("Please select part to delete.");
            return;
        }
        Part partToDelete = partsTable.getSelectionModel().getSelectedItem();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)  {
            if(Inventory.deletePart(partToDelete)) {
                Alert confirmed = new Alert(Alert.AlertType.INFORMATION, "The part was deleted successfully.");
                confirmed.showAndWait();
            }
            partsTable.refresh();

        }
    }

    /**Delete Product button handler. Asks user to confirm before deleting. Informational dialog box informing user product was deleted.
     * @param event Delete Product button click
     * */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        if(productTable.getSelectionModel().getSelectedItem() == null) {
            selectProduct.setText("Please select product to delete.");
            return;
        }
        Product productToDelete = productTable.getSelectionModel().getSelectedItem();

        if(productToDelete.getAssociatedParts().isEmpty() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please remove associated parts before deleting product.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if(Inventory.deleteProduct(productToDelete)) {
                Alert confirmed = new Alert(Alert.AlertType.INFORMATION, "The product was deleted successfully.");
                confirmed.showAndWait();
            }
            productTable.refresh();
        }
    }

    /**Exit button event handler. Asks users to confirm before closing.
     * @param event Exit button click
     * */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)  {
            System.exit(0);
        }

    }

    /** Function that handles loading a new controller
     * @param event button click
     * @param pathToScene Path to new scene's controller file
     * */
    public void newScreen(ActionEvent event, String pathToScene) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(pathToScene));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Initializes the 2 tables on the Main Screen with the ObservableLists of Parts and Products.
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        partsTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }

}