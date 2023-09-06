import java.util.*;
public class customer extends productcatalogue implements login {

    private customer_details cust;
    private HashMap<String,String> customerdetail=new HashMap<>();
    private admin Admin;
    private HashMap<String,customer_details> Customer=new HashMap<>();
    private HashMap<String,product> product_list;
    private HashMap<Double,deal> deal_list;
    private Scanner sc=new Scanner(System.in);
    public void customerlogin(){
        System.out.println("Enter name");
        String c1=sc.nextLine();
        System.out.println("Enter password");
        String c2=sc.nextLine();
        if(customerdetail.containsKey(c2)){
            if(customerdetail.get(c2).equals(c1)){
                this.cust=Customer.get(c1);
                login_page();
            }
        }

    }
    public void signup(){
        System.out.println("Enter name");
        String c1=sc.nextLine();
        System.out.println("Enter password");
        String c2=sc.nextLine();
        System.out.println("customer successfully registered!!");
        customerdetail.put(c2,c1);
        customer_details c=new customer_details(c1);
        Customer.put(c1,c);
    }
    public customer(admin a) {
        super(a);
        this.Admin=a;
        this.deal_list=Admin.getDeallist();
        this.product_list=Admin.getProduct_list();
    }

    @Override
    public void login_page() {
        System.out.println("Welcome "+cust.getName() +"!!\n");
        while (true){
            System.out.println("1) browse products\n" + "2) browse deals\n" + "3) add a product to cart\n" + "4) add products in deal to cart\n" + "5) view coupons\n" + "6) check account balance\n" + "7) view cart\n" + "8) empty cart\n" + "9) checkout cart\n" + "10) upgrade customer status\n" + "11) Add amount to wallet\n" + "12) back\n");
            int i=Integer.parseInt(sc.nextLine());
            if(i==1){
                browse_products();
            } else if (i==2) {
                Show_Available_Deals();
            } else if (i==3) {
                add_product_to_cart();
            } else if (i==4) {
                add_products_in_deal_to_cart();
            } else if (i==5) {
                view_coupons();
            } else if (i==6) {
                check_account_balance();
            } else if (i==7) {
                view_cart();
            } else if (i==8) {
                empty_cart();
            } else if (i==9) {
                checkout_cart();
            } else if (i==10) {
                upgrade_customer_status();
            } else if (i==11) {
                Add_amount_to_wallet();
            } else if (i==12) {
                System.out.println("Bye "+cust.getName()+"!!");
                break;
            }
            else {
                System.out.println("Incorrect Input. Please choose correct option\n");
            }
        }
    }

    private void Add_amount_to_wallet() {
        System.out.println("Enter amount to add");
        double p=Double.parseDouble(sc.nextLine());
        cust.setBalance(p);
        System.out.println("Your updated balance is "+cust.getBalance());
    }

    private void upgrade_customer_status() {
        System.out.println("Current status:"+cust.getStatus()+"\n");
        System.out.print("Choose new status:");
        String a1= sc.nextLine();
        if(cust.getStatus().equals("ELITE")){
            System.out.println("You are already ELITE. You can't upgrade status.Before current subscription ends");
            return;
        }
        if(cust.getStatus().equals("PRIME") && !a1.equals("ELITE")){
            System.out.println("You can't downgrade your status.");
            return;
        }
        cust.setStatus(a1);
        if(a1.equals("PRIME")){
            if(cust.getBalance()<200){
                System.out.println("Insufficient balance!! Please try again");
            }
            else{
                cust.setStatus(a1);
                cust.setBalance(-200);
            }
        }
        if(a1.equals("ELITE")){
            if(cust.getBalance()<300){
                System.out.println("Insufficient balance!! Please try again");
            }
            else{
                cust.setStatus(a1);
                cust.setBalance(-300);
            }
        }
        System.out.println("Status updated to "+cust.getStatus());
    }

    private void checkout_cart() {
        if(cust.getStatus().equals("PRIME")){
            double total=0;
            double prime=0;
            HashMap<String,product> p=cust.getCart();
            for (Map.Entry<String, product> e : p.entrySet()){
               if( e.getValue().getStock()>cust.getQuantity(e.getValue())){
                    total+=(e.getValue().discount("NORMAL"))*cust.getQuantity(e.getValue());
                    prime+=(e.getValue().discount("PRIME"))*cust.getQuantity(e.getValue());
                }
                else{
                   System.out.println("Order can't be placed few product out of stock");
                   return;
                }
            }
            double dealtotal=0;
            HashMap<Double,deal> p2=cust.getDealcart();
            for (Map.Entry<Double, deal> e : p2.entrySet()){
                if (product_list.get(e.getValue().getP1id()).getStock()==0 || product_list.get(e.getValue().getP2id()).getStock()==0){
                    System.out.println("Deal product out of stock.Order can't be placed");
                }
                else{
                    dealtotal+=e.getValue().getPrime();
                }
            }
            int discount=5;
            if(cust.getCoupons().size()>0){
                if(cust.getCoupons().get(0)>5){
                    discount=cust.getCoupons().get(0);
                }
            }
            double discounted=(total-(total*discount)/100);
            if(discounted>prime){
                if((dealtotal+prime+100+(total/50))>cust.getBalance()){
                    System.out.println("Insufficent balance. Please try again");
                    return ;
                }
                System.out.println("Your order is placed successfully. Details:");
                HashMap<String,product> pop=cust.getCart();
                for (Map.Entry<String, product> e : pop.entrySet()){
                    e.getValue().decrease_stock(cust.getQuantity(e.getValue()));
                    System.out.println("Product Name:"+e.getValue().getProduct_name());
                    System.out.println("Product ID:"+e.getKey());
                    for(String j:e.getValue().getDetails()){
                        System.out.println(j);
                    }
                    System.out.println("Price:Rs"+e.getValue().discount("NORMAL"));
                }
                for (Map.Entry<Double, deal> e : p2.entrySet()){
                    System.out.println("Deal ID="+e.getKey());
                    deal d=e.getValue();
                    String p1=d.getP1id();
                    System.out.println("Product ID of first product:"+p1);
                    System.out.println("Product Details:-");
                    product pi1=p.get(p1);
                    pi1.decrease_stock(1);
                    System.out.println("Product Name:"+pi1.getProduct_name());
                    ArrayList<String> po1=pi1.getDetails();
                    for (String i:po1){
                        System.out.println(i);
                    }
                    System.out.println("Price:"+pi1.discount("NORMAL"));
                    String p3=d.getP2id();
                    System.out.println("Product ID of second product:"+p3);
                    System.out.println("Product Detaisl:-");
                    product pi2=p.get(p3);
                    pi2.decrease_stock(1);
                    System.out.println("Product Name:"+pi2.getProduct_name());
                    ArrayList<String> po2=pi2.getDetails();
                    for (String i:po2){
                        System.out.println(i);
                    }
                    System.out.println("Price:"+pi2.discount("NORMAL"));

                }

                System.out.println("Delivery charges: Rs 100 + 2% of "+total+" =100+ "+(total/50)+" =Rs"+100+(total/50));
                System.out.println("Total cost=Rs"+(dealtotal+prime+100+(dealtotal+total/50)));
                System.out.println("Order placed. It will be delivered in 3-6 days.");
                return;
            }
            if((dealtotal+total+100+(total/50))>cust.getBalance()){
                System.out.println("Insufficient balance.Please try again");
                return;
            }
            System.out.println("Your order is placed successfully. Details:");
            HashMap<String,product> po=cust.getCart();
            for (Map.Entry<String, product> e : po.entrySet()){
                e.getValue().decrease_stock(cust.getQuantity(e.getValue()));
                System.out.println("Product Name:"+e.getValue().getProduct_name());
                System.out.println("Product ID:"+e.getKey());
                for(String j:e.getValue().getDetails()){
                    System.out.println(j);
                }
                System.out.println("Price:Rs"+e.getValue().discount("NORMAL"));
            }
            for (Map.Entry<Double, deal> e : p2.entrySet()) {
                System.out.println("Deal ID=" + e.getKey());
                deal d = e.getValue();
                String p1 = d.getP1id();
                System.out.println("Product ID of first product:" + p1);
                System.out.println("Product Details:-");
                product pi1 = p.get(p1);
                pi1.decrease_stock(1);
                System.out.println("Product Name:" + pi1.getProduct_name());
                ArrayList<String> po1 = pi1.getDetails();
                for (String i : po1) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi1.discount("NORMAL"));
                String p3 = d.getP2id();
                System.out.println("Product ID of second product:" + p3);
                System.out.println("Product Details:-");
                product pi2 = p.get(p3);
                pi2.decrease_stock(1);
                System.out.println("Product Name:" + pi2.getProduct_name());
                ArrayList<String> po2 = pi2.getDetails();
                for (String i : po2) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi2.discount("NORMAL"));
            }
            System.out.println("Delivery charges: Rs 100 + 2% of "+total+dealtotal+" =100+ "+((total+dealtotal)/50)+" =Rs"+(100+((dealtotal+total)/50)));

            System.out.println("Discount:"+discount+"% of "+total+" ="+(total*discount)/100);
            System.out.println("Total cost=Rs"+(dealtotal+total+(100+(total/50))-(total*discount)/100));
            System.out.println("Order placed. It will be delivered in 3-6 days");
            if((total-(total*discount)/100)>5000){
                cust.generatecoupons();
            }
        }
        if(cust.getStatus().equals("ELITE")){
            double total=0;
            double prime=0;
            HashMap<String,product> p=cust.getCart();
            for (Map.Entry<String, product> e : p.entrySet()){
                if( e.getValue().getStock()>cust.getQuantity(e.getValue())){
                    total+=(e.getValue().discount("NORMAL"))*cust.getQuantity(e.getValue());
                    prime+=(e.getValue().discount("ELITE"))*cust.getQuantity(e.getValue());
                }
                else{
                    System.out.println("Order can't be placed few product out of stock");
                    return;
                }
            }
            double dealtotal=0;
            HashMap<Double,deal> p2=cust.getDealcart();
            for (Map.Entry<Double, deal> e : p2.entrySet()){
                if (product_list.get(e.getValue().getP1id()).getStock()==0 || product_list.get(e.getValue().getP2id()).getStock()==0){
                    System.out.println("Deal product out of stock.Order can't be placed");
                }
                else{
                    dealtotal+=e.getValue().getElite();
                }
            }
            int discount=10;
            if(cust.getCoupons().size()>0){
                if(cust.getCoupons().get(0)>10){
                    discount=cust.getCoupons().get(0);
                }
            }
            double discounted=(total-(total*discount)/100);
            if(discounted>prime){
                if((dealtotal+prime+100+(total/50))>cust.getBalance()){
                    System.out.println("Insufficent balance. Please try again");
                    return ;
                }
                System.out.println("Your order is placed successfully. Details:");
                HashMap<String,product> pop=cust.getCart();
                for (Map.Entry<String, product> e : pop.entrySet()){
                    e.getValue().decrease_stock(cust.getQuantity(e.getValue()));
                    System.out.println("Product Name:"+e.getValue().getProduct_name());
                    System.out.println("Product ID:"+e.getKey());
                    for(String j:e.getValue().getDetails()){
                        System.out.println(j);
                    }
                    System.out.println("Price:Rs"+e.getValue().discount("NORMAL"));
                }
                for (Map.Entry<Double, deal> e : p2.entrySet()){
                    System.out.println("Deal ID="+e.getKey());
                    deal d=e.getValue();
                    String p1=d.getP1id();
                    System.out.println("Product ID of first product:"+p1);
                    System.out.println("Product Details:-");
                    product pi1=p.get(p1);
                    pi1.decrease_stock(1);
                    System.out.println("Product Name:"+pi1.getProduct_name());
                    ArrayList<String> po1=pi1.getDetails();
                    for (String i:po1){
                        System.out.println(i);
                    }
                    System.out.println("Price:"+pi1.discount("NORMAL"));
                    String p3=d.getP2id();
                    System.out.println("Product ID of second product:"+p3);
                    System.out.println("Product Detaisl:-");
                    product pi2=p.get(p3);
                    pi2.decrease_stock(1);
                    System.out.println("Product Name:"+pi2.getProduct_name());
                    ArrayList<String> po2=pi2.getDetails();
                    for (String i:po2){
                        System.out.println(i);
                    }
                    System.out.println("Price:"+pi2.discount("NORMAL"));

                }
                System.out.println("Delivery charges: Rs 100");
                System.out.println("Total cost=Rs"+(prime+dealtotal+100));
                prime+=100+dealtotal;
                cust.setBalance(-prime);
                System.out.println("Order placed. It will be delivered within 2 days.");
                return;
            }
            if(total+100>cust.getBalance()){
                System.out.println("Insufficient balance.Please try again");
                return;
            }
            System.out.println("Your order is placed successfully. Details:");
            HashMap<String,product> po=cust.getCart();
            for (Map.Entry<String, product> e : po.entrySet()){
                e.getValue().decrease_stock(cust.getQuantity(e.getValue()));
                System.out.println("Product Name:"+e.getValue().getProduct_name());
                System.out.println("Product ID:"+e.getKey());
                for(String j:e.getValue().getDetails()){
                    System.out.println(j);
                }
                System.out.println("Price:Rs"+e.getValue().discount("NORMAL"));
            }
            for (Map.Entry<Double, deal> e : p2.entrySet()) {
                System.out.println("Deal ID=" + e.getKey());
                deal d = e.getValue();
                String p1 = d.getP1id();
                System.out.println("Product ID of first product:" + p1);
                System.out.println("Product Details:-");
                product pi1 = p.get(p1);
                pi1.decrease_stock(1);
                System.out.println("Product Name:" + pi1.getProduct_name());
                ArrayList<String> po1 = pi1.getDetails();
                for (String i : po1) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi1.discount("NORMAL"));
                String p3 = d.getP2id();
                System.out.println("Product ID of second product:" + p3);
                System.out.println("Product Details:-");
                product pi2 = p.get(p3);
                pi2.decrease_stock(1);
                System.out.println("Product Name:" + pi2.getProduct_name());
                ArrayList<String> po2 = pi2.getDetails();
                for (String i : po2) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi2.discount("NORMAL"));
            }
            System.out.println("Delivery charges:Rs 100");
            System.out.println("Discount:"+discount+"% of "+total+" ="+(total*discount)/100);
            System.out.println("Total cost=Rs"+(total+dealtotal+100-(total*discount)/100));
            System.out.println("Order placed. It will be delivered within 2 days");
            total=dealtotal+total+100-(total*discount)/100;
            cust.setBalance(-total);
            int rand = (int)(Math.random() * 2) + 0;
            if(rand!=0) {
                System.out.println("Congratulations. You got extra quantity of a product as a surpise.Thanks for ordering");
            }
            if((total-(total*discount)/100)>5000){
                cust.generatecoupons();
            }
        }
        if(cust.getStatus().equals("NORMAL")){
            double total=0;
            HashMap<String,product> p=cust.getCart();
            for (Map.Entry<String, product> e : p.entrySet()){
                if( e.getValue().getStock()>cust.getQuantity(e.getValue())){
                    total+=(e.getValue().discount("NORMAL"))*cust.getQuantity(e.getValue());
                }
                else{
                    System.out.println("Order can't be placed few product out of stock");
                    return;
                }
            }
            HashMap<Double,deal> p2=cust.getDealcart();
            for (Map.Entry<Double, deal> e : p2.entrySet()){
                if (product_list.get(e.getValue().getP1id()).getStock()==0 || product_list.get(e.getValue().getP2id()).getStock()==0){
                    System.out.println("Deal product out of stock.Order can't be placed");
                }
                else{
                    total+=e.getValue().getNormal();
                }
            }
            int discount=0;
            if((total+100+(total/20))>cust.getBalance()){
                System.out.println("Insufficient balance.Please try again");
                return;
            }
            System.out.println("Your order is placed successfully. Details:");
            HashMap<String,product> po=cust.getCart();
            for (Map.Entry<String, product> e : po.entrySet()){
                e.getValue().decrease_stock(cust.getQuantity(e.getValue()));
                System.out.println("Product Name:"+e.getValue().getProduct_name());
                System.out.println("Product ID:"+e.getKey());
                for(String j:e.getValue().getDetails()){
                    System.out.println(j);
                }
                System.out.println("Price:Rs"+e.getValue().discount("NORMAL"));
            }
            for (Map.Entry<Double, deal> e : p2.entrySet()) {
                System.out.println("Deal ID=" + e.getKey());
                deal d = e.getValue();
                String p1 = d.getP1id();
                System.out.println("Product ID of first product:" + p1);
                System.out.println("Product Details:-");
                product pi1 = p.get(p1);
                pi1.decrease_stock(1);
                System.out.println("Product Name:" + pi1.getProduct_name());
                ArrayList<String> po1 = pi1.getDetails();
                for (String i : po1) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi1.discount("NORMAL"));
                String p3 = d.getP2id();
                System.out.println("Product ID of second product:" + p3);
                System.out.println("Product Details:-");
                product pi2 = p.get(p3);
                pi2.decrease_stock(1);
                System.out.println("Product Name:" + pi2.getProduct_name());
                ArrayList<String> po2 = pi2.getDetails();
                for (String i : po2) {
                    System.out.println(i);
                }
                System.out.println("Price:" + pi2.discount("NORMAL"));
            }
            System.out.println("Delivery charges: Rs 100 + 5% of "+total+" =100+ "+(total/20)+" =Rs"+(100+(total/20)));

            System.out.println("Discount:0%");
            System.out.println("Total cost=Rs"+(total+(100+(total/20))-(total*discount)/100));
            System.out.println("Order placed. It will be delivered in 7-10 days");
        }
    }

    private void empty_cart() {
        cust.remove();
        System.out.println("Cart successfully emptied");
    }
    private void view_cart() {
        HashMap<String,product> cart=cust.getCart();
        HashMap<Double,deal> dealcart=cust.getDealcart();
        if(cart.size()==0 && dealcart.size()==0){
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Total number of products in cart:"+cart.size());
        for (Map.Entry<String, product> e : cart.entrySet()){
            System.out.println("Product ID:"+e.getKey());
            System.out.println("Product Name:"+e.getValue().getProduct_name());
            ArrayList<String> s=e.getValue().getDetails();
            for(String j:s){
                System.out.println(j);
            }
            System.out.println("Product Price:"+e.getValue().discount("NORMAL"));
            System.out.println("Product Quantity:"+cust.getQuantity(e.getValue()));
        }
        System.out.println("Total number of deals in cart:"+dealcart.size());
        for (Map.Entry<Double, deal> e : dealcart.entrySet()){
            System.out.println("Deal ID:"+e.getKey());
            System.out.println("Product 1 id:"+e.getValue().getP1id());
            product p=product_list.get(e.getValue().getP1id());
            System.out.println("Product 1 name:"+p.getProduct_name());
            ArrayList <String> s=p.getDetails();
            for(String j:s){
                System.out.println(j);
            }
            System.out.println("Product 2 id:"+e.getValue().getP2id());
            p=product_list.get(e.getValue().getP2id());
            System.out.println("Product 2 name:"+p.getProduct_name());
            s=p.getDetails();
            for(String j:s){
                System.out.println(j);
            }
            System.out.println("Deal price for normal customer:"+e.getValue().getNormal());
        }
    }
    private void check_account_balance() {
        System.out.println( "Current balance:"+cust.getBalance());
    }

    private void view_coupons() {
        ArrayList<Integer> coup=cust.getCoupons();
        if(coup.size()==0){
            System.out.println("Oops!No coupons currently.Shop to get coupons");
            return;
        }
        System.out.println("Congratulaions you have "+coup.size()+" coupons.Use them to get discount. Details of coupon are:");
        for(Integer j:coup){
            System.out.println(j+"% discount coupon");
        }
    }

    private void add_products_in_deal_to_cart() {
        System.out.print("Enter Deal ID:");
        double db=Double.parseDouble(sc.nextLine());
        while(!deal_list.containsKey(db)){
            System.out.println("Please enter correct deal id.");
            db=Double.parseDouble(sc.nextLine());
        }
        cust.addtodealcart(db,deal_list.get(db));
    }

    private void add_product_to_cart() {
        System.out.print("Enter product ID:");
        String sa=sc.nextLine();
        while(!product_list.containsKey(sa)){
            System.out.println("Incorrect product id entered.Please try again");
            sa=sc.nextLine();
        }
        product p=product_list.get(sa);
        System.out.print("Enter quantity:");
        int qu=Integer.parseInt(sc.nextLine());
        if(qu>p.getStock()){
            System.out.println("Oops. We don't have requested amount of quantity for product");
        }
        else{
            cust.addtocart(sa,p,qu);
        }
    }
}
