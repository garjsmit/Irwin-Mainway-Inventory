package Model;

/** Inherits methods from Part. */
public class InHouse extends Part{

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /**
    * @return Returns machineID.
    * */
    public int getMachineID() { return machineID;    }

    /**
     * @param machineID Set machineID.
     * */
    public void setMachineID(int machineID) {  this.machineID = machineID;    }

}