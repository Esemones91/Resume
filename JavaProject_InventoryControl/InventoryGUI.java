// filename: InventoryGUI.java
// assignment: Final Project: Inventory Control
// author: Erick Semones
// date: 9-30-2024

/* Create a GUI for user-friendly interface using
 * Swing elements. Primarily interacts with
 * InventoryManager functions.
 */

 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.*;
 import javax.swing.table.DefaultTableModel;
 
 public class InventoryGUI extends JFrame 
 {
 
    private InventoryManager inventoryManager; // Manage inventory on the backend
    private JTextField itemNameField, categoryField, quantityField, priceField, reorderField;
    private JTable inventoryTable; // Displays items
    private DefaultTableModel tableModel;
 
    // Constructor
    public InventoryGUI(InventoryManager inventoryManager) 
    {
        this.inventoryManager = inventoryManager;
        initializeGUI();
        loadInventoryTable();
    } 
 
    private void initializeGUI() {
        // Set up the main frame
        setTitle("Inventory Control System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
        // Create a panel for inputs
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
 
        // Create input fields
        itemNameField = new JTextField();
        categoryField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        reorderField = new JTextField();
 
        // Add labels and input fields to the input panel
        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(itemNameField);
        
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        
        inputPanel.add(new JLabel("Reorder Level:"));
        inputPanel.add(reorderField);
 
        // Add input panel to the frame
        add(inputPanel, BorderLayout.NORTH);
 
        // Create a table to display inventory
        tableModel = new DefaultTableModel(new String[]
            {"Name", "Category", "Quantity", "Price", "Reorder Level"}, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);
 
        // Create buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton saveButton = new JButton("Save Inventory");
        JButton loadButton = new JButton("Load Inventory");
        JButton newInventoryButton = new JButton("New Inventory");
        JButton generateReportButton = new JButton("Generate Report");
    JButton reorderAlertButton = new JButton("Reorder Alert");
 
        // Add action listeners for buttons
        addButton.addActionListener(new AddItemListener());
        updateButton.addActionListener(new UpdateItemListener());
        deleteButton.addActionListener(new DeleteItemListener());
        saveButton.addActionListener(new SaveInventoryListener());
        loadButton.addActionListener(new LoadInventoryListener());
        newInventoryButton.addActionListener(new NewInventoryListener());
        generateReportButton.addActionListener(new GenerateReportListener());
        reorderAlertButton.addActionListener(new ReorderAlertListener());
 
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(newInventoryButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(reorderAlertButton);
 
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // load inventory into the table
    private void loadInventoryTable()
    {
        tableModel.setRowCount(0); // clears the table
        for(Item item : inventoryManager.getInventory())
        {
            tableModel.addRow(new Object[]
            {
                item.getName(),
                item.getCategory(),
                item.getQuantity(),
                item.getPrice(),
                item.getReorderLvl()

            });
        }
    }
 
    // ActionListener for adding an item
    private class AddItemListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            try
            {
                String itemName = itemNameField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                int reorderLevel = Integer.parseInt(reorderField.getText());

                // Call backend to add item
                inventoryManager.addItem(itemName, category, quantity, price, reorderLevel);
                loadInventoryTable();
 
                // Clear fields after adding
                itemNameField.setText("");
                categoryField.setText("");
                quantityField.setText("");
                priceField.setText("");
                reorderField.setText("");
 
                JOptionPane.showMessageDialog(null, 
                    "Item added successfully!");
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, 
                    "Please enter valid numbers for Quantity, Price, and ReorderLevel");
            } 
        }
    }
 
   // ActionListener for updating an item
    private class UpdateItemListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String itemName = itemNameField.getText().trim();
            String category = categoryField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String priceText = priceField.getText().trim();
            String reorderLevelText = reorderField.getText().trim();

            // Check for empty fields
            if (itemName.isEmpty() || category.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || reorderLevelText.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, "All fields must be filled out.");
                return;
            }

            try 
            {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);
                int reorderLevel = Integer.parseInt(reorderLevelText);

                // Call backend to update item
                inventoryManager.updateItem(itemName, category, quantity, price, reorderLevel);
                loadInventoryTable(); // Refresh the inventory table

                // Clear fields after updating
                itemNameField.setText("");
                categoryField.setText("");
                quantityField.setText("");
                priceField.setText("");
                reorderField.setText("");

                JOptionPane.showMessageDialog(null, 
                    "Item updated successfully!");
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(null, 
                    "Please enter valid numbers for Quantity, Price, and Reorder Level.");
            }
        }
    }

    // ActionListener for deleting an item
    private class DeleteItemListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String itemName = itemNameField.getText().trim();

            if (itemName.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, 
                    "Item name cannot be empty.");
                return;
            }

            // Call backend to delete item
            inventoryManager.deleteItem(itemName);
            loadInventoryTable(); // Refresh the inventory table

            // Clear field after deletion
            itemNameField.setText("");

            JOptionPane.showMessageDialog(null, "Item deleted successfully!");
        }
    }

    // ActionListener for saving inventory
    private class SaveInventoryListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            inventoryManager.saveInventory();
            JOptionPane.showMessageDialog(null, 
                "Inventory saved successfully!");
        }
    }

    // ActionListener for loading inventory
    private class LoadInventoryListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            inventoryManager.loadInventory();
            loadInventoryTable(); // Refresh the inventory table
            JOptionPane.showMessageDialog(null, 
                "Inventory loaded successfully!");
        }
    }

    // ActionListener for creating a new inventory
    private class NewInventoryListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            inventoryManager = new InventoryManager(); // Reinitialize the InventoryManager
            tableModel.setRowCount(0); // Clear the table
            JOptionPane.showMessageDialog(null, "New inventory created.");
        }
    }

    // ActionListener for generating the inventory report
    private class GenerateReportListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Call backend to generate the report
            inventoryManager.generateReport();
        }
    }

    // ActionListener for generating the reorder alert report
    private class ReorderAlertListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Call backend to check and display reorder alerts
            inventoryManager.checkReorderAlerts();
        }
    }

    // Run the program
    public static void main(String[] args) 
    {
        InventoryManager manager = new InventoryManager();
        InventoryGUI gui = new InventoryGUI(manager);
        gui.setVisible(true);
    }
}