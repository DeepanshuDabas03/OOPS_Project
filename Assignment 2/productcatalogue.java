import java.util.*;
public class productcatalogue implements catalogue,deals {
    private admin a;
    public productcatalogue(admin a){
        this.a=a;
    }
    @Override
    public void Show_Available_Deals(){
        HashMap<Double, deal> h= a.getDeallist();
        if(h.size()==0){
            System.out.println("Dear User, there are no deals for now!!! Please check regularly for exciting deals.\n");
            return;
        }
        HashMap<String,product> p=a.getProduct_list();
        if(p.size()<2){
            System.out.println("Dear User, there are no deals for now!!! Please check regularly for exciting deals.\n");
            return;
        }
        for (Map.Entry<Double, deal> e : h.entrySet()){
            System.out.println("Deal ID="+e.getKey());
            deal d=e.getValue();
            String p1=d.getP1id();
            System.out.println("Product ID of first product:"+p1);
            System.out.println("Product Details:-");
            product pi1=p.get(p1);
            System.out.println("Product Name:"+pi1.getProduct_name());
            ArrayList<String> po1=pi1.getDetails();
            for (String i:po1){
                System.out.println(i);
            }
            System.out.println("Price:"+pi1.discount("NORMAL"));
            System.out.println("Quantity in stock:"+pi1.getStock());
            String p2=d.getP2id();
            System.out.println("Product ID of second product:"+p2);
            System.out.println("Product Detaisl:-");
            product pi2=p.get(p2);
            System.out.println("Product Name:"+pi2.getProduct_name());
            ArrayList<String> po2=pi2.getDetails();
            for (String i:po2){
                System.out.println(i);
            }
            System.out.println("Price:"+pi2.discount("NORMAL"));
            System.out.println("Quantity in stock:"+pi2.getStock());
        }
    }
    @Override
    public void browse_products() {
        HashMap <String,category> b =a.getCategorylist();

        if(b.size()==0){
            System.out.println("Dear User, no products are available for sale.Please check back late for exciting products");
            return;
        }
        for (Map.Entry<String, category> e : b.entrySet()){
            category c=e.getValue();
            System.out.println("Category ID: "+e.getKey());
            System.out.println("Category name:"+c.getCategory_name());
            HashMap<String,product> d=c.getProduct_list();
            for (Map.Entry<String, product> f : d.entrySet()){
                System.out.println("Product ID:"+f.getKey());
                product p1=f.getValue();
                System.out.println("Product Name:"+p1.getProduct_name());
                ArrayList<String> s1=p1.getDetails();
                for(String i:s1){
                    System.out.println(i);
                }
                System.out.println("Price:"+p1.discount("NORMAL"));
                System.out.println("Quantity left in stock:"+p1.getStock());
                System.out.println();
            }
        }
    }
}
