// Filename Inventory
// Asgnmnt  Final Project
// Author   Erick Semones
// Date     9-7-2024
// Updated  9-21-2024

/* manages a collection of Item objects. */



import java.util.ArrayList;

 public class Inventory 
 {
    private ArrayList<Item> items;
    private boolean isRemoved;
    
    // Constructor
    public Inventory()
    {
        items = new ArrayList<>();
        isRemoved = false;
    }

    // add items to the inventory list
    public void addItem(Item item)
    {
        items.add(item);
    }

    // update the quantity and reorder level of individual items
    public void updateItem(Item item)
    {
        for(Item i : items)
        {
            // is the item in the inventory?
            if(i.getName().equals(item.getName()))
            {
                i.setQuantity(item.getQuantity());
                i.setReorderLvl(item.getReorderLvl());
                i.setPrice(item.getPrice()); 
                return;
            }
            else
            {
                System.out.println("Item not found");
            }
        }
        
    }

    // delete item entries in the inventory list
    public boolean deleteItem(String itemName)
    {
        items.removeIf(i -> i.getName().equals(itemName));
        return isRemoved = true;
    }

    // retrieve a list of items
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    // retrieve a list of items that are below their reorder level
    public ArrayList<Item> getLowStockItems()
    {
        ArrayList<Item> lowStockItems = new ArrayList<>();
        for(Item i : items)
        {
            // is item quantity less than its reorder level?
            if(i.getQuantity() < i.getReorderLvl())
            {
                lowStockItems.add(i);
            }
        }
        return lowStockItems;
    }

    // find an item by its name
    public Item findItemByName(String itemName)
    {
        for(Item item : items)
        {
            // does item name match an item in the inventory?
            if(item.getName().equalsIgnoreCase(itemName))
            {
                return item;
            }
        }
        return null;
    }
}
