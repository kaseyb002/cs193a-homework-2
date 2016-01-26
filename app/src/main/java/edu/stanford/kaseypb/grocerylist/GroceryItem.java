package edu.stanford.kaseypb.grocerylist;

/**
 * Created by kaseybaughan on 1/25/16.
 */
public class GroceryItem {
    private String item;
    private int quantity = 1;

    public GroceryItem(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity += 1;
    }

    public void decrementQuantity() {
        if(this.quantity > 1) {
          this.quantity -= 1;
        }
    }
}
