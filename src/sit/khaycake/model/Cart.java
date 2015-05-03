package sit.khaycake.model;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;

import java.util.List;

/**
 * Created by Pasuth on 23/4/2558.
 */
public class Cart  {
    private List<Item> items;

    class Item implements Comparable<Item> {
        private Product product;
        private int qty;

        public Item(Product product, int qty){
            this.product = product;
            this.qty = qty;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }


        @Override
        public int compareTo(Item item) {
            return this.getProduct().getId() - item.getProduct().getId();
        }

        public boolean equals(Object object)
        {
            boolean result = false;

            if (object != null && object instanceof Product)
            {
                result = this.getProduct().getId() == ((Product) object).getId();
            }

            return result;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int add(int id) throws Exception{
        return add(id, 1);
    }

    public int add(int id, int qty) throws Exception{
        Product product = (Product) SQL.findById(Product.class, id);
        if(product != null){
            if(!this.items.contains(product)){
                Item item = new Item(product, qty);
                this.items.add(item);
                return 0; // new item
            }else{
                Item item = new Item(product, qty);
                item.setProduct(product);
                item.setQty(qty);
                Item it = this.getItems().get(this.getItems().indexOf(item));
                it.setQty(it.getQty() + qty);
                return 1; // old item
            }

        }else {
            return -1; // not found
        }
    }

    public int reduce(int id) throws Exception{
        return add(id, 1);
    }

    public int reduce(int id, int qty) throws Exception{
        Product product = (Product) SQL.findById(Product.class, id);
        if(product != null){
            if(!this.items.contains(product)){
                return -1; // not found
            }else{
                Item item = new Item(product, qty);
                item.setProduct(product);
                item.setQty(qty);
                Item it = this.getItems().get(this.getItems().indexOf(item));
                it.setQty(it.getQty() - qty);
                if(it.getQty()<=0){
                    remove(item.getProduct().getId());
                    return 0; //remove item
                }
                return 1; // resduce item
            }

        }else {
            return -1; // not found
        }
    }

    public void remove(int id) throws Exception{
        Product product = (Product) SQL.findById(Product.class, id);
        Item tmp = new Item(product, 0);
        items.remove(tmp);

    }


    public int getSize(){
        return items.size();
    }


}
