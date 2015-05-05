package sit.khaycake.model;

import sit.khaycake.database.SQL;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Pasuth on 23/4/2558.
 */
public class Cart {
    private transient HashMap<Integer, Item> itemsMap;
    private List<Item> items;

    class Item implements Comparable<Item> {
        private Product product;
        private int qty;
        private double total;

        public Item(Product product, int qty) throws Exception {
            this.product = product;
            this.setQty(qty);
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) throws Exception {
            this.qty = qty;
            this.calculateTotal();
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }

        public double getTotal() {
            return total;
        }

        private void calculateTotal() throws Exception {
            Product prod = SQL.findById(Product.class, this.product.getId());
            if (prod != null) {
                List<ProductSale> sales = prod.getSales();
                Collections.sort(sales);
                int qty = this.qty;
                double price = 0d;
                int i = 0;
                while (i < sales.size()) {
                    if (qty - sales.get(i).getQty() >= 0) {
                        qty -= sales.get(i).getQty();
                        price += sales.get(i).getPrice();
                    } else {
                        i++;
                    }
                }
                this.total = price;
            }


        }


        @Override
        public int compareTo(Item item) {
            return this.getProduct().getId() - item.getProduct().getId();
        }

        public boolean equals(Object object) {
            boolean result = false;

            if (object != null && object instanceof Product) {
                result = this.getProduct().getId() == ((Product) object).getId();
            }

            return result;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "product=" + product +
                    ", qty=" + qty +
                    ", total=" + total +
                    '}';
        }
    }

    public static Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        return cart != null ? cart : new Cart();
    }

    public List<Item> getItems() {
        if(this.items == null)
            this.items = new ArrayList<>();
        Collection c = itemsMap.values();
        Iterator<Item> it = c.iterator();
        while(it.hasNext())
            this.items.add(it.next());
        return this.items;
    }

    public void add(int id) throws Exception {
        add(id, 1);
    }

    public void add(int id, int qty) throws Exception {
        if (this.itemsMap == null)
            this.itemsMap = new HashMap<>();
        this.items = new ArrayList<>();
        Product product = SQL.findById(Product.class, id);
        if (product != null) {
            if (this.itemsMap.get(id) != null) {
                Item item = this.itemsMap.get(id);
                this.itemsMap.get(id).setQty(item.getQty() + qty);
            } else {
                Item item = new Item(product, qty);
                this.itemsMap.put(product.getId(), item);
            }
            this.items.addAll(this.itemsMap.values());
        }
    }

    public void set(int id, int qty) throws Exception {
        if (this.itemsMap == null)
            this.itemsMap = new HashMap<>();
        this.items = new ArrayList<>();
        Product product = SQL.findById(Product.class, id);
        if (product != null) {
            if (this.itemsMap.get(id) != null) {
                this.itemsMap.get(id).setQty(qty);
            } else {
                Item item = new Item(product, qty);
                this.itemsMap.put(product.getId(), item);
            }
            this.items.addAll(this.itemsMap.values());
        }
    }

    public int getSize() {
        return items.size();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}