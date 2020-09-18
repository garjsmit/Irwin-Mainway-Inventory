package View_Controller;

        import Model.InHouse;
        import Model.Inventory;
        import Model.Outsourced;
        import Model.Part;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

        /**Add Parts Controller*/
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRad, outsourceRad;

    @FXML
    private TextField partNameTxt, partStockTxt, partPriceTxt, partMaxTxt, partMinTxt, machIdCompNameTxt;

    @FXML
    private Label machineIdOrCompanyName;

    /**Save Part button handler. Inventory must be within min and max, and max must be smaller than min, else there's an error. Also generates an error dialog if input type generates an Error.i
     * @param event Save button click
     * */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            Part part = new InHouse(0, "Generic", 0.99, 10, 0, 100, 0);
            int id = Inventory.partIdGenerator();
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partStockTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part stock max must be larger than min.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part inventory must be between min and max.");
                alert.showAndWait();
            } else {
                if (inHouseRad.isSelected()) {
                    int machineID = Integer.parseInt(machIdCompNameTxt.getText());
                    part = new InHouse(id, name, price, stock, min, max, machineID);

                } else if (outsourceRad.isSelected()) {
                    String companyName = machIdCompNameTxt.getText();
                    part = new Outsourced(id, name, price, stock, min, max, companyName);
                }

                Inventory.addPart(part);
                newScreen(event, "/View_Controller/MainScreen.fxml");

            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid text or numbers in fields.");
            alert.showAndWait();
        }

    }

    /**Outsource selected button handler
     * @param event radiobutton selected
     * */
    @FXML
    void onActionOutsourceSelected(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }

     /**InHouse selected button handler
      * @param event radiobutton selected
      * */
    @FXML
    void onActionInHouseSelected(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine ID");
    }

    /**Exit button handler
     * @param event exit button clicked
     * */
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        newScreen(event, "/View_Controller/MainScreen.fxml");

    }

    /** Function that handles loading a new controller
    * @param event button click
    * @param pathToScene Path to new scene's controller file
    * */
    public void newScreen(ActionEvent event, String pathToScene) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(pathToScene));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

}
