// Filename Item
// Asgnmnt  Final Project
// Author   Erick Semones
// Date     9-7-2024 (10-2-2024)

/* allows for Item object instantiation in Inventory class.
it includes fields, constructor, accessors, and
methods for interacting with other classes..*/



 public class Item
 {
    private String name, category;
    private int quantity, reorderLvl;
    private double price;

    // constructor
    public Item(String name, String category, int quantity, double price, int reorderLvl)
    {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.reorderLvl = reorderLvl;
        this.price = price;
    }

    // return the value of name
    public String getName()
    {
        return name;
    }
    
    // return the value of category
    public String getCategory()
    {
        return category;
    }
    
    // return the value of quantity
    public int getQuantity()
    {
        return quantity;
    }
    
    // return the value of reorderLvl
    public int getReorderLvl()
    {
        return reorderLvl;
    }
    
    // return the value of price
    public double getPrice()
    {
        return price;
    }

    // set the value for quantity
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    
    // set the value for reorderLvl
    public void setReorderLvl(int reorderLvl)
    {
        this.reorderLvl = reorderLvl;
    }
    
    // set the value for price
    public void setPrice(double price)
    {
        this.price = price;
    }

    // retrieve a string representation of the Item class for display
    @Override 
    public String toString()
    {
        return String.format
            ("Name: %s, Category: %s, Quantity: %d, Reorder Level: %d, Price: %.2f",
            name, category, quantity, reorderLvl, price);
    }
}



