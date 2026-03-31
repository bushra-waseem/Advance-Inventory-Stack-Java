package semesterproject;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.Stack;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InventoryManagementSystem extends JFrame {
    
    Stack<Item> stack;
    DefaultTableModel model;
    JTable table;
    JTextField nameBox, qtyBox, priceBox;
    JTextArea logBox;
    JLabel countLabel, valueLabel, statusLabel;

    public InventoryManagementSystem() {
        stack = new Stack<Item>();
        
        setTitle("Inventory Management System");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BorderLayout(10, 10));
        
        // Top Panel
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(41, 128, 185));
        top.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel title = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Stack-Based Data Structure Implementation");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(236, 240, 241));
        
        JPanel titles = new JPanel(new GridLayout(2, 1));
        titles.setBackground(new Color(41, 128, 185));
        titles.add(title);
        titles.add(subtitle);
        top.add(titles, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);
        
        // Split
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(650);
        
        // Left Panel
        JPanel left = new JPanel(new BorderLayout(10, 10));
        left.setBackground(new Color(236, 240, 241));
        left.setBorder(new EmptyBorder(10, 10, 10, 5));
        
        // Table Panel
        JPanel tableBox = new JPanel(new BorderLayout());
        tableBox.setBackground(Color.WHITE);
        tableBox.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(41, 128, 185), 2), 
                "Current Inventory Stack", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(41, 128, 185)),
            new EmptyBorder(10, 10, 10, 10)));
        
        String[] cols = {"Position", "Item Name", "Quantity", "Price ($)", "Total Value ($)"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        tableBox.add(new JScrollPane(table));
        left.add(tableBox, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonBox = new JPanel(new BorderLayout(10, 10));
        buttonBox.setBackground(Color.WHITE);
        buttonBox.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(41, 128, 185), 2), 
                "Inventory Operations", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(41, 128, 185)),
            new EmptyBorder(10, 10, 10, 10)));
        
        JPanel inputs = new JPanel(new GridLayout(3, 2, 10, 10));
        inputs.setBackground(Color.WHITE);
        
        JLabel l1 = new JLabel("Item Name:");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameBox = new JTextField();
        nameBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameBox.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(189, 195, 199)), new EmptyBorder(5, 5, 5, 5)));
        
        JLabel l2 = new JLabel("Quantity:");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        qtyBox = new JTextField();
        qtyBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        qtyBox.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(189, 195, 199)), new EmptyBorder(5, 5, 5, 5)));
        
        JLabel l3 = new JLabel("Unit Price ($):");
        l3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        priceBox = new JTextField();
        priceBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        priceBox.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(189, 195, 199)), new EmptyBorder(5, 5, 5, 5)));
        
        inputs.add(l1);
        inputs.add(nameBox);
        inputs.add(l2);
        inputs.add(qtyBox);
        inputs.add(l3);
        inputs.add(priceBox);
        
        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 0));
        buttons.setBackground(Color.WHITE);
        buttons.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton btn1 = makeButton("PUSH (Add)", new Color(39, 174, 96), new Color(30, 140, 70));
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addItem(); }
        });
        
        JButton btn2 = makeButton("POP (Remove)", new Color(231, 76, 60), new Color(180, 60, 50));
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { removeItem(); }
        });
        
        JButton btn3 = makeButton("PEEK (View Top)", new Color(41, 128, 185), new Color(30, 100, 150));
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { viewTop(); }
        });
        
        nameBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) { if (e.getKeyCode() == 10) addItem(); }
        });
        
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttonBox.add(inputs, BorderLayout.CENTER);
        buttonBox.add(buttons, BorderLayout.SOUTH);
        left.add(buttonBox, BorderLayout.SOUTH);
        
        // Right Panel
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.setBackground(new Color(236, 240, 241));
        right.setBorder(new EmptyBorder(10, 5, 10, 10));
        
        JPanel stats = new JPanel(new GridLayout(2, 1, 5, 5));
        stats.setBackground(Color.WHITE);
        stats.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(41, 128, 185), 2), 
                "Statistics", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(41, 128, 185)),
            new EmptyBorder(10, 10, 10, 10)));
        
        countLabel = new JLabel("Total Items in Stack: 0");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel = new JLabel("Total Inventory Value: $0.00");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        stats.add(countLabel);
        stats.add(valueLabel);
        right.add(stats, BorderLayout.NORTH);
        
        JPanel logs = new JPanel(new BorderLayout());
        logs.setBackground(Color.WHITE);
        logs.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(41, 128, 185), 2), 
                "Activity Log", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(41, 128, 185)),
            new EmptyBorder(10, 10, 10, 10)));
        
        logBox = new JTextArea();
        logBox.setEditable(false);
        logBox.setFont(new Font("Consolas", Font.PLAIN, 12));
        logs.add(new JScrollPane(logBox));
        right.add(logs, BorderLayout.CENTER);
        
        split.setLeftComponent(left);
        split.setRightComponent(right);
        add(split, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(52, 73, 94));
        bottom.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        statusLabel = new JLabel("System Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        
        JLabel copy = new JLabel("© 2025 Inventory Management System | Semester Project");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copy.setForeground(new Color(189, 195, 199));
        
        bottom.add(statusLabel, BorderLayout.WEST);
        bottom.add(copy, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    public JButton makeButton(String text, Color bg, Color hover) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    public void addItem() {
        String name = nameBox.getText().trim();
        String qty = qtyBox.getText().trim();
        String price = priceBox.getText().trim();
        
        if (name.isEmpty() || qty.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int q = Integer.parseInt(qty);
            double p = Double.parseDouble(price);
            
            if (q <= 0 || p < 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be positive and price cannot be negative!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            stack.push(new Item(name, q, p));
            refresh();
            nameBox.setText("");
            qtyBox.setText("");
            priceBox.setText("");
            nameBox.requestFocus();
            
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            logBox.append("[" + time + "] PUSH: Added '" + name + "' (Qty: " + q + ", Price: $" + p + ")\n");
            logBox.setCaretPosition(logBox.getDocument().getLength());
            
            setStatus("Item added successfully!", new Color(39, 174, 96));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and price!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeItem() {
        if (stack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stack is empty! No items to remove.", "Empty Stack", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (JOptionPane.showConfirmDialog(this, "Remove the top item from the stack?", "Confirm Pop Operation", JOptionPane.YES_NO_OPTION) == 0) {
            Item item = stack.pop();
            refresh();
            
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            logBox.append("[" + time + "] POP: Removed '" + item.name + "' (Qty: " + item.qty + ", Price: $" + item.price + ")\n");
            logBox.setCaretPosition(logBox.getDocument().getLength());
            
            setStatus("Item removed successfully!", new Color(231, 76, 60));
        }
    }

    public void viewTop() {
        if (stack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stack is empty! No items to view.", "Empty Stack", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Item item = stack.peek();
        String msg = "Top Item Details:\n\n" + "Item Name: " + item.name + "\n" + "Quantity: " + item.qty + "\n" +
                     "Unit Price: $" + String.format("%.2f", item.price) + "\n" + "Total Value: $" + String.format("%.2f", item.getTotal()) + "\n" + "Position: Top of Stack";
        
        JOptionPane.showMessageDialog(this, msg, "Peek Operation", JOptionPane.INFORMATION_MESSAGE);
        
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        logBox.append("[" + time + "] PEEK: Viewed '" + item.name + "' at top of stack\n");
        logBox.setCaretPosition(logBox.getDocument().getLength());
    }

    public void setStatus(String text, Color color) {
        statusLabel.setText(text);
        statusLabel.setForeground(color);
        new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("System Ready");
                statusLabel.setForeground(Color.WHITE);
            }
        }).start();
    }

    public void refresh() {
        for (int i = model.getRowCount() - 1; i >= 0; i--) model.removeRow(i);

        if (stack.isEmpty()) {
            countLabel.setText("Total Items in Stack: 0");
            valueLabel.setText("Total Inventory Value: $0.00");
            return;
        }

        Stack<Item> temp = new Stack<Item>();
        for (int i = 0; i < stack.size(); i++) temp.push(stack.get(i));

        Stack<Item> display = new Stack<Item>();
        while (!temp.isEmpty()) display.push(temp.pop());

        int pos = 1;
        while (!display.isEmpty()) {
            Item item = display.pop();
            Object[] data = {pos, item.name, item.qty, String.format("%.2f", item.price), String.format("%.2f", item.getTotal())};
            model.addRow(data);
            pos++;
        }

        int total = 0;
        double value = 0.0;
        for (int i = 0; i < stack.size(); i++) {
            total = total + stack.get(i).qty;
            value = value + stack.get(i).getTotal();
        }
        
        countLabel.setText("Total Items in Stack: " + stack.size());
        valueLabel.setText("Total Inventory Value: $" + String.format("%.2f", value));
    }

    class Item {
        String name;
        int qty;
        double price;
        
        Item(String n, int q, double p) {
            name = n;
            qty = q;
            price = p;
        }
        
        double getTotal() {
            return qty * price;
        }
    }

    public static void main(String[] args) {
        new InventoryManagementSystem();
    }
}