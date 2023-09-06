import java.util.*;
class category{
    private String category_name;
    private HashMap<String, product> product_list=new HashMap<>();;
    public category(String category_name, String id,product p) {
        this.category_name = category_name;
        this.product_list.put(id,p);
    }
    public void addproduct(String id,product p){
            this.product_list.put(id, p);
    }

    public String getCategory_name() {
        return category_name;
    }

    public int delete(String id){
        this.product_list.remove(id);
        if( product_list.isEmpty()){
            return 1;
        }
        else{
            return 0;
        }
    }
    public HashMap<String, product> getProduct_list() {
        return product_list;
    }
}
