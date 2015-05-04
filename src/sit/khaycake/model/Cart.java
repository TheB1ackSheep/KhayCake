package sit.khaycake.model;

import sit.khaycake.database.SQL;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pasuth on 23/4/2558.
 */
public class Cart {
    private HashMap<Integer, Item> items;

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

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, Item> items) {
        this.items = items;
    }

    public void add(int id) throws Exception {
        add(id, 1);
    }

    public void add(int id, int qty) throws Exception {
        if (this.items == null)
            this.items = new HashMap<>();
        Product product = SQL.findById(Product.class, id);
        if (product != null) {
            if (this.items.get(id) != null) {
                Item item = this.items.get(id);
                this.items.get(id).setQty(item.getQty() + qty);
            } else {
                Item item = new Item(product, qty);
                this.items.put(product.getId(), item);
            }
        }
    }

    public void set(int id, int qty) throws Exception {
        if (this.items == null)
            this.items = new HashMap<>();
        Product product = SQL.findById(Product.class, id);
        if (product != null) {
            if (this.items.get(id) != null) {
                this.items.get(id).setQty(qty);
            } else {
                Item item = new Item(product, qty);
                this.items.put(product.getId(), item);
            }
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
