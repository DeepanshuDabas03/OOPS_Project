import java.util.ArrayList;

public class product {
    private String product_name;
    private ArrayList<String> details;
    private double prime;
    private double elite;
    private double normal;
    private int stock;

    public int getStock() {
        return stock;
    }

    public product(String product_name, ArrayList<String> details, double price, int stock) {
        this.product_name = product_name;
        this.details = details;
        this.prime = price-(price/20);
        this.normal=price;
        this.elite=price-((price)/10);
        this.stock=stock;
    }

    public String getProduct_name() {
        return product_name;
    }

    public ArrayList<String> getDetails() {
        return details;
    }
    public void setDiscount(int elite,int prime,int normal){
    this.elite=this.elite-this.elite*(elite)/100;
    this.prime=this.prime-this.prime*(prime)/100;
    this.normal=this.normal-this.normal*(normal)/100;
    }
    public void decrease_stock(int q){
        this.stock-=q;
    }
    public double discount(String status){
        if(status.equals("ELITE")){
            return this.elite;
        } else if (status.equals("PRIME")) {
            return this.prime;
        }else{
            return this.normal;
        }
    }
}
