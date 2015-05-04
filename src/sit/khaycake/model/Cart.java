package sit.khaycake.model;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasuth on 23/4/2558.
 */
public class Cart  {
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

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

        @Override
        public boolean equals(Object object)
        {
            boolean result = false;

            if (object != null && object instanceof Item)
            {
                result = this.getProduct().getId() == ((Item) object).getProduct().getId();
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

    public Cart add(Product product) throws Exception{
        return add(product, 1);
    }

    public Cart add(Product product, int qty) throws Exception{
        Item item = new Item(product, qty);
        if(!this.items.contains(item)){
            this.items.add(item);
            return this; // new item
        }else{
            item.setProduct(product);
            item.setQty(qty);
            Item it = this.getItems().get(this.getItems().indexOf(item));
            it.setQty(it.getQty() + qty);
            return this; // old item
        }
    }

    public Cart reduce(Product product) throws Exception{
        return reduce(product, 1);
    }

    public Cart reduce(Product product, int qty) throws Exception{
        Item item = new Item(product, 0);
        if(!this.items.contains(item)){
            return this; // not found
        }else{
            Item it = this.getItems().get(this.getItems().indexOf(item));
            it.setQty(it.getQty() - qty);
            if(it.getQty()<=0){
                remove(item.getProduct());
                return this; //remove item
            }
            return this; // resduce item
        }
    }

    public void remove(Product product) throws Exception{
        //Product product = (Product) SQL.findById(Product.class, id);
        Item tmp = new Item(product, 0);
        items.remove(tmp);

    }


    public int getSize(){
        return items.size();
    }


}
