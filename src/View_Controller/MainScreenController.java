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
        import javafx.scene.control.Button;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part,Integer> partStockCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;



    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productStockCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField searchText;

    @FXML
    void onActionDeleteProduct(ActionEvent event) {

    }

    @FXML
    void onActionModifyPartScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/ModifyPart.fxml");

    }

    @FXML
    void onActionAddPartScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/AddPart.fxml");

    }

    @FXML
    void onActionModifyProductScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/ModifyProduct.fxml");
    }

    @FXML
    void onActionAddProductScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/AddProduct.fxml");
    }

    @FXML
    void onActionSearch(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    public void newScreen(ActionEvent event, String pathToScene) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(pathToScene));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        partsTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }
}