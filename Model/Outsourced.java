package Model;

/** Inherits methods from Part. */
public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return Gets companyName
     * */
    public String getCompanyName() { return companyName;    }

    /**
     * @param companyName sets companyName*/
    public void setCompanyName(String companyName) { this.companyName = companyName;    }





}
