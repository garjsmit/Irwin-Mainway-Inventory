package View_Controller;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.RadioButton;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRad;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partStockTxt;

    @FXML
    private TextField machineIDTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private RadioButton outsourceRad;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    void onActionSavePart(ActionEvent event) {

    }

    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/MainScreen.fxml");

    }

    public void newScreen(ActionEvent event, String pathToScene) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(pathToScene));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

}
