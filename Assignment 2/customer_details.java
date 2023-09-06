import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class customer_details {
    private String name;
    private double balance;
    private String status;
    private HashMap<String,product> cart=new HashMap<>();
    private HashMap<Double ,deal> dealcart=new HashMap<>();
    private ArrayList<Integer>coupons=new ArrayList<>();
    private HashMap<product,Integer> quantity=new HashMap<>();

    public int getQuantity(product p) {
        return quantity.get(p);
    }

    public void setQuantity(product p,int q) {
        this.quantity.put(p,q);
    }

    public ArrayList<Integer> getCoupons() {
        return coupons;
    }
    public void generatecoupons(){
        if(this.status.equals("PRIME")){
            int no_of_coupons = (int)(Math.random() * 2) + 1;
            System.out.print("You have won "+no_of_coupons+" coupons of ");
            for(int i=0;i<no_of_coupons;i++){
                int rand = (int)(Math.random() * 11) + 5;
                System.out.print(rand+"%,");
                coupons.add(rand);
            }
            System.out.println("discount. Congratulations!!");
            coupons.sort(Collections.reverseOrder());
        }
        else if(this.status.equals("ELITE")){
            int no_of_coupons = (int)(Math.random() * 2) + 3;
            for(int i=0;i<no_of_coupons;i++){
                int rand = (int)(Math.random() * 11) + 5;
                coupons.add(rand);
            }
            coupons.sort(Collections.reverseOrder());
        }
    }
    public customer_details(String name){
        this.name=name;
        this.balance=1000;
        this.status="NORMAL";
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public HashMap<String, product> getCart() {
        return cart;
    }

    public HashMap<Double, deal> getDealcart() {
        return dealcart;
    }

    public void addtocart(String p1, product p,int i){
        this.cart.put(p1,p);
        quantity.put(p,i);
    }
    public void addtodealcart(double p1,deal d){
        this.dealcart.put(p1,d);
    }

    public void setBalance(double balance) {
        this.balance +=balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void remove(){
        this.cart.clear();
        this.dealcart.clear();
    }

}
