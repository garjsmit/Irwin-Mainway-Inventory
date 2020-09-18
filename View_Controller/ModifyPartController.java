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

public class ModifyPartController implements Initializable{

    Stage stage;
    Parent scene;
    Part part;

    @FXML
    private RadioButton inHouseRad, outsourceRad;

    @FXML
    private Label machineIdOrCompanyName;

    @FXML
    private TextField partIdTxt, partNameTxt, partStockTxt, partPriceTxt, partMinTxt, partMaxTxt, machIdCompNameTxt;

    /**Save Part button handler. Inventory must be within min and max, and max must be smaller than min, else there's an error. Also generates an error dialog if input type generates an Error.i
     * Saves new part in same index of the old part.
     * @param event Save button click
     * */
    @FXML
    void onActionUpdatePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(partIdTxt.getText());
            String name = partNameTxt.getText();
            double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partStockTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product stock max must be larger than min.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product inventory must be between min and max.");
                alert.showAndWait();
            } else {

                if (inHouseRad.isSelected()) {
                    int machineID = Integer.parseInt(machIdCompNameTxt.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.updateInHouse(Inventory.getAllParts().indexOf(part), newPart);
                } else if (outsourceRad.isSelected()) {
                    String companyName = machIdCompNameTxt.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updateOutsource(Inventory.getAllParts().indexOf(part), newPart);
                }

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

    /**setPart fills in the fields on this screen with the data from the part selected on the Main Screen
     * @param part Part selected from Main Screen*/
    public void setPart(Part part) {
        this.part = part;

        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partStockTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partMaxTxt.setText(String.valueOf((part.getMax())));

        if(part instanceof InHouse) {
            inHouseRad.setSelected(true);
            machineIdOrCompanyName.setText("Machine ID");
            machIdCompNameTxt.setText(String.valueOf(((InHouse)part).getMachineID()));

        }
        if(part instanceof Outsourced) {
            outsourceRad.setSelected(true);
            machineIdOrCompanyName.setText("Company Name");
            machIdCompNameTxt.setText(String.valueOf(((Outsourced)part).getCompanyName()));
        }
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
