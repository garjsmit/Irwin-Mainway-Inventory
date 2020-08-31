package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IrwinMainwayInventoryManagement extends Application {

    @Override
    public void init(){

    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Irwin Mainway Inventory Management");
        primaryStage.setScene(new Scene(root, 1149, 426));
        primaryStage.show();
    }

    @Override
    public void stop(){

    }


    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        Part bag = new InHouse(1341, "Bag", .39, 6, 0,100, 9);
        Part button = new InHouse(5494, "Button", .25, 4,0,100, 8);
        Part johnnyFigure = new InHouse(3929, "Johnny Figurine", 1.93, 34, 0, 100, 8);
        Part teddyBear = new InHouse(3843, "Teddy Bear", 3.83, 32, 0, 100, 3);
        Part tie = new InHouse(2984, "Tie", .89, 12, 0, 100, 93);
        inventory.addPart(bag);
        inventory.addPart(button);
        inventory.addPart(johnnyFigure);
        inventory.addPart(teddyBear);
        inventory.addPart(tie);

        Part brokenGlass = new Outsourced(2543, "Broken Glass", 3.78, 9, 0, 100, "Glass, Inc");
        Part knives = new Outsourced(2484, "Knives, pair", .89, 38, 93, 93, "Knives 'R' Us");
        Part chainsaw = new Outsourced(4593, "Chainsaw", 54.99, 38, 32, 94, "Chainsaws, Inc");
        inventory.addPart(brokenGlass);
        inventory.addPart(knives);
        inventory.addPart(chainsaw);

        Product johnnySwitchblade = new Product(3939, 99, 23, 485, "Johnny Switchblade Adventure Punk", 34.99);
        Product bagOGlass = new Product( 2948, 33, 29, 188, "Bag O' Glass", 29.99);
        Product teddyChainsaw = new Product(7342, 44, 19, 183, "Teddy Chainsaw Bear", 39.99);

        johnnySwitchblade.addAssociatedPart(johnnyFigure);
        johnnySwitchblade.addAssociatedPart(knives);
        johnnySwitchblade.addAssociatedPart(button);
        bagOGlass.addAssociatedPart(bag);
        bagOGlass.addAssociatedPart(brokenGlass);
        teddyChainsaw.addAssociatedPart(teddyBear);
        teddyChainsaw.addAssociatedPart(tie);
        teddyChainsaw.addAssociatedPart(chainsaw);

        inventory.addProduct(bagOGlass);
        inventory.addProduct(teddyChainsaw);
        inventory.addProduct(johnnySwitchblade);
        inventory.addProduct(new Product(1343, 17, 10, 500, "Mr. Skingrafter", 30.87));
        inventory.addProduct(new Product( 2894, 28, 17, 48, "Doggie Dentist", 37.46));

        launch(args);
    }
}
