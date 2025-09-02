// filename InventoryManager
// asgnmnt  Inventory Control System
// author   Erick Semones
// date     10-01-2024 (updated)

import java.util.ArrayList;
import javax.swing.*;

public class InventoryManager 
{
    private Inventory inventory;

    // constructor
    public InventoryManager() 
    {
        this.inventory = new Inventory();
        loadInventory();
    }

    // add item to inventory list
    public void addItem(String name, String category, int quantity, 
        double price, int reorderLvl) 
    {
        // is the item name empty?
        if (name.trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(null, 
                "Item name cannot be empty.");
            return;
        }

        // does the item already exist?
        if (findItem(name) != null) 
        {
            JOptionPane.showMessageDialog(null, 
                "Item with this name already exists.");
            return;
        }

        Item item = new Item(name, category, quantity, price, reorderLvl);
        inventory.addItem(item);
    }

    // update existing items in inventory list
    public void updateItem(String name, String category, int quantity,
        double price, int reorderLvl)
    {
        // is the item string empty?
        if (name.trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(null, 
                "Item name cannot be empty.");
            return;
        }

        // does the item already exist?
        if (findItem(name) == null) 
        {
            JOptionPane.showMessageDialog(null, 
                "Item with this name does not exist.");
            return;
        }

        Item updatedItem = new Item(name, category, quantity, price, reorderLvl);
        inventory.updateItem(updatedItem);
    }

    // delete item from inventory by name
    public void deleteItem(String name)
    {
        inventory.deleteItem(name);
    }

    // retrieve inventory list
    public ArrayList<Item> getInventory() 
    {
        return inventory.getItems();
    }

    // search inventory list for item
    public Item findItem(String name)
    {
        return inventory.findItemByName(name);
    }

    // check to see if items are below reorder level
    public void checkReorderAlerts() 
    {
        ArrayList<Item> lowStockItems = inventory.getLowStockItems();

        // do low stock items exist?
        if (!lowStockItems.isEmpty()) 
        {
            StringBuilder alertMessage = new StringBuilder("Items needing reorder:\n");

            // build list of low stock items
            for (Item item : lowStockItems) 
            {
                alertMessage.append(item.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(null, alertMessage.toString());
        } 
        else 
        {
            JOptionPane.showMessageDialog(null, "No items need reordering.");
        }
    }

    // create inventory report
    public void generateReport() 
    {
        StringBuilder report = new StringBuilder("Inventory Report:\n");
        for (Item item : inventory.getItems()) 
        {
            report.append(item.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    // load inventory from file using FileHandler
    public void loadInventory() 
    {
        ArrayList<Item> loadedItems = FileHandler.loadInventory();
        for (Item item : loadedItems) 
        {
            inventory.addItem(item);
        }
    }

    // save inventory to file using FileHandler
    public void saveInventory() 
    {
        FileHandler.saveInventory(inventory.getItems());
    }
}