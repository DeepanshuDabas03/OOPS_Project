import java.util.*;

public class FLIPZON {
    public static void main(String[] Args){
        admin a=new admin();
        Scanner sc=new Scanner(System.in);
        customer c=new customer(a);
        productcatalogue pc=new productcatalogue(a);
        while(true){
        System.out.println("WELCOME TO FLIPZON \n" + "1) Enter as Admin\n" + "2) Explore the Product Catalog\n" + "3) Show Available Deals\n" + "4) Enter as Customer\n" + "5) Exit the Application");
        int n=Integer.parseInt(sc.nextLine());
        if(n==1){
            a.login_page();
        }
        else if(n==2){
            pc.browse_products();
        }
        else if(n==3){
            pc.Show_Available_Deals();
        }
        else if(n==4){
            while(true){
            System.out.println("1) Sign up\n" + "2) Log in\n" + "3) Back");
            int abp=Integer.parseInt(sc.nextLine());
            if(abp==3){
                break;
            }
            else if(abp==1){
                c.signup();
            }
            else{
                c.customerlogin();
            }
            }
        }
        else if(n==5){
            System.out.println("Thank for using application");
            break;
        }
        else{
            System.out.println("Incorrect input please try again.");
        }
        }
    }
}
