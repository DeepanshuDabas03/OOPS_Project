import java.util.*;
public class admin implements login{
    private HashMap<String,String> m=new HashMap<>();
    private HashMap<Double,deal>  deallist = new HashMap<>();
    private HashMap<String,category>  categorylist = new HashMap<>();
    private HashMap<String,product>  product_list = new HashMap<>();
    private  HashSet<String> nameset = new HashSet<>();


    private  HashSet<String> catnameset = new HashSet<>();
    private double dealid=1.1;
    private Scanner sc=new Scanner(System.in);
    public admin(){
        this.m.put("Deepanshu","Deepanshu2003");
        this.m.put("TA","TA123");
        this.m.put("admin","Admin123");
    }

    @Override
    public void login_page() {

        System.out.println("Dear Admin,\n" + "Please enter your username and password");
        String username=sc.nextLine();
        String password=sc.nextLine();
        if(this.m.containsKey(username)){
            if(this.m.get(username).equals(password)){
                System.out.println("Welcome "+username+"!!!");
                welcome();
            }
            else{
                System.out.println("Incorrect Login Password. Please try again with correct login creditinals.");
            }
        }
        else{
            System.out.println("Incorrect Username.Please try again with correct login creditinals");
        }
    }

    public HashMap<Double, deal> getDeallist() {
        return deallist;
    }

    public HashMap<String, product> getProduct_list() {
        return product_list;
    }

    public HashMap<String, category> getCategorylist() {
        return categorylist;
    }
    private void Add_category(){
            System.out.println("Add category ID");
            String a=sc.nextLine();
            while(a == null || a.trim().isEmpty()){
                System.out.println("Category ID can't be empty.Please enter correct ID");
                a=sc.nextLine();
            }
            while(this.categorylist.containsKey(a)){
                System.out.println("Dear Admin, the category ID is already used!!! Please set a different and a unique category ID");
                System.out.println("Add category ID");
                a=sc.nextLine();
            }
             System.out.println("Add name of the category");
                String b=sc.nextLine();
                while(b == null || b.trim().isEmpty()){
                    System.out.println("Category name can't be empty.Please enter category name");
                    b=sc.nextLine();
                }
                while(catnameset.contains(b)){
                    System.out.println("Dear admin. A category with same name already exist please try again");
                    b=sc.nextLine();
                }
                catnameset.add(b);
                System.out.println("Add a Product:-");
                System.out.print("Product Name:");
                String c=sc.nextLine();
                while(nameset.contains(c))
                {
                    System.out.println("Product With same name already exist please try again");
                    System.out.print("Product Name:");
                    c=sc.nextLine();
                }
                nameset.add(c);
                System.out.print("Enter Product Id:");
                String pid=sc.nextLine();
                while(product_list.containsKey(pid)){
                    System.out.println("A product with same key already exist. Please use unique key");
                    pid=sc.nextLine();
                }
                System.out.println("Now Enter product details. Once you are finished entering details please type 'EXIT'.Do not enter price now.Enter after exiting");
                ArrayList <String> temp=new ArrayList<>();
                String d=sc.nextLine();
                if(!d.equals("EXIT")){
                    temp.add(d);
                }
                while(!d.equals("EXIT")){
                    d=sc.nextLine();
                    if(!d.equals("EXIT")){
                        temp.add(d);
                    }
                }
                System.out.print("Enter price (in Rs):");
                int e=Integer.parseInt(sc.nextLine());
                System.out.print("Enter Stock:");
                int f=Integer.parseInt(sc.nextLine());
                product p=new product(c,temp,e,f);
                category cat=new category(b,pid,p);
                product_list.put(pid,p);
                categorylist.put(a,cat);

    }
    private void Delete_category(){
        System.out.print("Enter Category ID(Please note product associated with the given category will also be deleted)");
        String c=sc.nextLine();
        while(!categorylist.containsKey(c)){
            System.out.println("No Category was found with given key. Please try again");
            c=sc.nextLine();
        }
        category cat=categorylist.get(c);
        HashMap<String, product> pro= cat.getProduct_list();
        for (Map.Entry<String, product> e : pro.entrySet()){
            if(this.product_list.containsKey(e.getKey()))
            {
                this.nameset.remove(e.getValue().getProduct_name());
                this.product_list.remove(e.getKey());
            }
        }
        catnameset.remove(cat.getCategory_name());
        categorylist.remove(c);
    }
    private void Add_Product(){
        System.out.println("Enter category ID");
        String cid=sc.nextLine();
        while(!categorylist.containsKey(cid)){
            System.out.println("Please enter correct category ID");
            System.out.println("Enter category ID");
            cid=sc.nextLine();
        }

        System.out.print("Product Name:");
        String c=sc.nextLine();
        while(nameset.contains(c))
        {
            System.out.println("Product With same name already exist please try again");
            System.out.print("Product Name:");
            c=sc.nextLine();
        }
        nameset.add(c);
        System.out.print("Enter Product Id:");
        String pid=sc.nextLine();
        while(product_list.containsKey(pid)){
            System.out.println("A product with same key already exist. Please use unique key");
            pid=sc.nextLine();
        }
        System.out.println("Now Enter product details. Once you are finished entering details please type 'EXIT'.Do not enter price now.Enter after exiting");
        ArrayList <String> temp=new ArrayList<>();
        String d=sc.nextLine();
        if(!d.equals("EXIT")){
            temp.add(d);
        }
        while(!d.equals("EXIT")){
            d=sc.nextLine();
            if(!d.equals("EXIT")){
                temp.add(d);
            }
        }
        System.out.print("Enter price (in Rs):");
        int e=Integer.parseInt(sc.nextLine());
        System.out.print("Enter Stock:");
        int f=Integer.parseInt(sc.nextLine());
        product p=new product(c,temp,e,f);
        categorylist.get(cid).addproduct(pid,p);
        this.product_list.put(pid,p);
    }
    private void Delete_Product(){
       System.out.println("Please enter category id of product");
       String cpid=sc.nextLine();
       while(!categorylist.containsKey(cpid)){
           System.out.println("Incorrect category id, please try again with correct id " );
           cpid=sc.nextLine();
       }
       HashMap<String, product> pmap=categorylist.get(cpid).getProduct_list();
       System.out.println("Please enter product id");
       String pid=sc.nextLine();
       while(!pmap.containsKey(pid)){
           System.out.println("Please Enter correct product id");
           pid=sc.nextLine();
       }
       nameset.remove(product_list.get(pid).getProduct_name());
       int a=categorylist.get(cpid).delete(pid);

       if(a==1){
           System.out.println("Empty category. Want to add product else category will be deleted\n 1)Yes\n2)No");
           int ye=Integer.parseInt(sc.nextLine());
           if(ye==1){
               System.out.println("Add a Product:-");
               System.out.print("Product Name:");
               String c=sc.nextLine();
               while(nameset.contains(c))
               {
                   System.out.println("Product With same name already exist please try again");
                   System.out.print("Product Name:");
                   c=sc.nextLine();
               }
               nameset.add(c);
               System.out.print("Enter Product Id");
               pid=sc.nextLine();
               while(product_list.containsKey(pid)){
                   System.out.println("A product with same key already exist. Please use unique key");
                   pid=sc.nextLine();
               }
               System.out.println("Now Enter product details. Once you are finished entering details please type 'EXIT'.Do not enter price now.Enter after exiting");
               ArrayList <String> temp=new ArrayList<>();
               String d=sc.nextLine();
               if(!d.equals("EXIT")){
                   temp.add(d);
               }
               while(!d.equals("EXIT")){
                   d=sc.nextLine();
                   if(!d.equals("EXIT")){
                       temp.add(d);
                   }
               }
               System.out.print("Enter price (in Rs):");
               int e=Integer.parseInt(sc.nextLine());
               System.out.print("Enter Stock:");
               int f=Integer.parseInt(sc.nextLine());
               product p=new product(c,temp, e,f);
               categorylist.get(cpid).addproduct(pid,p);
               this.product_list.put(pid,p);

           }
           else{
               catnameset.remove( categorylist.get(cpid).getCategory_name());
               categorylist.remove(cpid);
           }
       }
   }
    private void Set_Discount_on_Product(){
        System.out.println("Dear Admin give the Product ID you want to add discount for");
        System.out.println("Enter the Product ID :");
        String pid=sc.nextLine();
        while(!product_list.containsKey(pid)){
            System.out.println("Incorrect product ID.Please try again with correct product id");
            pid=sc.nextLine();
        }
        product p=product_list.get(pid);
        System.out.print("Enter discount for Elite(in % terms):");
        int elite=Integer.parseInt(sc.nextLine());
        System.out.print("Enter discount for prime(in % terms):");
        int prime=Integer.parseInt(sc.nextLine());
        System.out.print("Enter discount for Normal(in % terms):");
        int normal=Integer.parseInt(sc.nextLine());
        while(elite<0 || prime<0 || normal<0 || elite>100 || prime>100 || normal>100){
            System.out.println("Discount should be in range 0-100.Please try again.");
            System.out.print("Enter discount for Elite(in % terms):");
            elite=Integer.parseInt(sc.nextLine());
            System.out.print("Enter discount for prime(in % terms):");
            prime=Integer.parseInt(sc.nextLine());
            System.out.print("Enter discount for Normal(in % terms):");
            normal=Integer.parseInt(sc.nextLine());
        }
        p.setDiscount(elite,prime,normal);
    }
    private void Add_giveaway_deal(){
        System.out.println("Dear Admin give the Product IDs you want to combine and giveaway a deal for");
        System.out.println("Enter the first Product ID :");
        String pid1=sc.nextLine();
        while(!product_list.containsKey(pid1)){
            System.out.println("Incorrect product id.Please try again\nEnter the first Product ID :");
            pid1=sc.nextLine();
        }
        System.out.println("Enter the second Product ID:");
        String pid2=sc.nextLine();
        while(!product_list.containsKey(pid2)){
            System.out.println("Incorrect product id.Please try again\nEnter the second Product ID :");
            pid2=sc.nextLine();
        }
        System.out.println("Enter the combined price(Should be less than their combined price):");
        product p1=product_list.get(pid1);
        product p2=product_list.get(pid2);
        System.out.print("Enter price for Elite:");
        int a=Integer.parseInt(sc.nextLine());
        while(a>=p1.discount("ELITE")+p2.discount("ELITE")){
            System.out.println("Price for Elite should be less than  orignal price.Orignal Price:"+(p1.discount("ELITE")+p2.discount("ELITE")));
            System.out.print("Enter price for Elite:");
            a=Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter price for Prime:");
        int b=Integer.parseInt(sc.nextLine());
        while(b>=p1.discount("PRIME")+p2.discount("PRIME")){
            System.out.println("Price for Prime should be less than  orignal price.Orignal Price:"+(p1.discount("PRIME")+p2.discount("PRIME")));
            System.out.print("Enter price for Prime:");
            b=Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter price for Normal:");
        int c=Integer.parseInt(sc.nextLine());
        while(c>=p1.discount("NORMAL")+p2.discount("NORMAL")){
            System.out.println("Price for Normal should be less than  orignal price.Orignal Price:"+(p1.discount("NORMAL")+p2.discount("NORMAL")));
            System.out.print("Enter price for Normal:");
            c=Integer.parseInt(sc.nextLine());
        }
        deal d=new deal(pid1,pid2,b,a,c);
        deallist.put(dealid,d);
        this.dealid+=0.1;
    }
   private void welcome() {
        while(true) {
            System.out.println("Please choose any one of the following actions:\n1) Add category\n" + "2) Delete category\n" + "3) Add Product\n" + "4) Delete Product\n" + "5) Set Discount on Product\n" + "6) Add giveaway deal\n" + "7) Back");
            int i = Integer.parseInt(sc.nextLine());
            if(i==1){
               Add_category();
            }
            else if(i==2){
                Delete_category();
            }
            else if(i==3){
                Add_Product();
            }
            else if(i==4){
                Delete_Product();
            }
            else if(i==5){
                Set_Discount_on_Product();
            }
            else if(i==6){
               Add_giveaway_deal();
            }
            else if(i==7){
                return;
            }
        }
    }
}
