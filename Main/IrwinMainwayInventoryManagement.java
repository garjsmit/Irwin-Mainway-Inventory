package Main;



import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**Irwin Mainway Inventory Management Program.
 * This class contains the Main method and loads the MainScreenController.*/
public class IrwinMainwayInventoryManagement extends Application {

    /**init method.*/
    @Override
    public void init(){    }

    /** Loads the MainScreenController.*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Irwin Mainway Inventory Management");
        primaryStage.setScene(new Scene(root, 1149, 426));
        primaryStage.show();
    }

    /**stop method.*/
    @Override
    public void stop(){    }

    /** Main method. Loads the test inventory. The idGenerator is a Part and Product method that is called here,
     * and the IDs are different each time the application is run, but the IDs are consistent throughout the running of each program.
     * In future versions, using a database to store the inventory will allow data persistence and data consistency with multiple users.
     * Also, creating different users will be helpful for recording received part orders and edits to parts and products.
     * */
    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        Part bag = new InHouse(Inventory.partIdGenerator(), "Bag", .39, 6, 0,100, 9);
        Part button = new InHouse(Inventory.partIdGenerator(), "Button", .25, 4,0,100, 8);
        Part johnnyFigure = new InHouse(Inventory.partIdGenerator(), "Johnny Figurine", 1.93, 34, 0, 100, 8);
        Part teddyBear = new InHouse(Inventory.partIdGenerator(), "Teddy Bear", 3.83, 32, 0, 100, 3);
        Part tie = new InHouse(Inventory.partIdGenerator(), "Tie", .89, 12, 0, 100, 93);
        inventory.addPart(bag);
        inventory.addPart(button);
        inventory.addPart(johnnyFigure);
        inventory.addPart(teddyBear);
        inventory.addPart(tie);

        Part brokenGlass = new Outsourced(Inventory.partIdGenerator(), "Broken Glass", 3.78, 9, 0, 100, "Glass, Inc");
        Part knives = new Outsourced(Inventory.partIdGenerator(), "Knives, pair", .89, 38, 93, 93, "Knives 'R' Us");
        Part chainsaw = new Outsourced(Inventory.partIdGenerator(), "Chainsaw", 54.99, 38, 32, 94, "Chainsaws, Inc");
        inventory.addPart(brokenGlass);
        inventory.addPart(knives);
        inventory.addPart(chainsaw);

        Product johnnySwitchblade = new Product(Inventory.productIdGenerator(), 99, 23, 485, "Johnny Switchblade Adventure Punk", 34.99);
        Product bagOGlass = new Product(Inventory.productIdGenerator(), 33, 29, 188, "Bag O' Glass", 29.99);
        Product teddyChainsaw = new Product(Inventory.productIdGenerator(), 44, 19, 183, "Teddy Chainsaw Bear", 39.99);

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
        inventory.addProduct(new Product(Inventory.productIdGenerator(), 17, 10, 500, "Mr. Skingrafter", 30.87));
        inventory.addProduct(new Product(Inventory.productIdGenerator(), 28, 17, 48, "Doggie Dentist", 37.46));

        launch(args);
    }
}
