import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Comb1nedApp extends JFrame {
    private Map<String, String> contacts = new HashMap<>(); // Shared contact data for Phonebook and Payroll
    private Map<String, java.util.List<String>> groups = new HashMap<>(); // Shared group data for Phonebook

    public Comb1nedApp() {
        setTitle("Combined System: Calculator, Phonebook, Payroll");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton btnCalculator = new JButton("Open Calculator");
        JButton btnPhonebook = new JButton("Open Phonebook");
        JButton btnPayroll = new JButton("Open Payroll System");

        btnCalculator.setFont(new Font("Arial", Font.BOLD, 16));
        btnPhonebook.setFont(new Font("Arial", Font.BOLD, 16));
        btnPayroll.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(btnCalculator);
        buttonPanel.add(btnPhonebook);
        buttonPanel.add(btnPayroll);

        add(buttonPanel, BorderLayout.CENTER);

        btnCalculator.addActionListener(e -> new Calculator().setVisible(true));
        btnPhonebook.addActionListener(e -> new PhonebookSystem().setVisible(true));
        btnPayroll.addActionListener(e -> new PayrollSystem().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CombinedApp().setVisible(true));
    }

    // Calculator Code
	public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String num1 = "", num2 = "", operator = "";
    private boolean isNewOperation = true;
    private ArrayList<String> history = new ArrayList<>();

    public Calculator() {
        // Set up the frame
        setTitle("Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        String[] buttons = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();  // Create the menu bar
        JMenu menu = new JMenu("Menu");  // Create the Menu

        JMenuItem computeItem = new JMenuItem("Compute");
        JMenuItem historyItem = new JMenuItem("View History");
        JMenuItem exitItem = new JMenuItem("Exit");

        computeItem.addActionListener(e -> compute());
        historyItem.addActionListener(e -> viewHistory());
        exitItem.addActionListener(e -> System.exit(0));

        // Add menu items to the menu
        menu.add(computeItem);
        menu.add(historyItem);
        menu.addSeparator();
        menu.add(exitItem);

        menuBar.add(menu);  // Add menu to the menu bar
        setJMenuBar(menuBar);  // Set the menu bar for the frame

        // Optionally, you can customize the layout further
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (isNewOperation) {
                display.setText(command);
                isNewOperation = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if ("/*-+".contains(command)) {
            if (operator.isEmpty()) {
                num1 = display.getText();
                operator = command;
                isNewOperation = true;
            }
        } else if ("=".equals(command)) {
            num2 = display.getText();
            try {
                double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                display.setText(String.valueOf(result));
                history.add(num1 + " " + operator + " " + num2 + " = " + result);
                isNewOperation = true;
            } catch (Exception ex) {
                display.setText("Error");
                isNewOperation = true;
            }
        } else if ("C".equals(command)) {
            display.setText("");
            num1 = num2 = operator = "";
            isNewOperation = true;
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": if (num2 == 0) throw new ArithmeticException("Cannot divide by zero"); return num1 / num2;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }

    // Method for Compute option in the menu
    private void compute() {
        // Trigger the '=' button functionality
        String currentText = display.getText();
        if (!currentText.isEmpty()) {
            actionPerformed(new ActionEvent(new JButton("="), ActionEvent.ACTION_PERFORMED, "="));
        }
    }

    // Method for View History option in the menu
    private void viewHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No history available.");
        } else {
            StringBuilder historyText = new StringBuilder("History:\n");
            for (String entry : history) {
                historyText.append(entry).append("\n");
            }
            JOptionPane.showMessageDialog(this, historyText.toString());
        }
    }
}
    // Phonebook Code
    class PhonebookSystem extends JFrame {
        private JTextField nameField, phoneField, groupField;
        private JTextArea contactArea;

        public PhonebookSystem() {
            setTitle("Phonebook System");
            setSize(600, 500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel panel = new JPanel(new BorderLayout());
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));

            nameField = new JTextField(15);
            phoneField = new JTextField(15);
            groupField = new JTextField(15);

            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Phone:"));
            inputPanel.add(phoneField);
            inputPanel.add(new JLabel("Group:"));
            inputPanel.add(groupField);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
            JButton addButton = new JButton("Add Contact");
            JButton deleteButton = new JButton("Delete Contact");
            JButton searchButton = new JButton("Search Contact");
            JButton updateButton = new JButton("Update Contact");
            JButton displayButton = new JButton("Display All");
            JButton addGroupButton = new JButton("Add Group");
            JButton deleteGroupButton = new JButton("Delete Group");

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(searchButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(displayButton);
            buttonPanel.add(addGroupButton);
            buttonPanel.add(deleteGroupButton);

            addButton.addActionListener(e -> addContact());
            deleteButton.addActionListener(e -> deleteContact());
            searchButton.addActionListener(e -> searchContact());
            updateButton.addActionListener(e -> updateContact());
            displayButton.addActionListener(e -> displayAllContacts());
            addGroupButton.addActionListener(e -> addGroup());
            deleteGroupButton.addActionListener(e -> deleteGroup());

            contactArea = new JTextArea(15, 50);
            contactArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(contactArea);

            panel.add(inputPanel, BorderLayout.NORTH);
            panel.add(buttonPanel, BorderLayout.CENTER);
            panel.add(scrollPane, BorderLayout.SOUTH);

            add(panel);
        }

        private void addContact() {
    String name = nameField.getText().trim();
    String phone = phoneField.getText().trim();
    String group = groupField.getText().trim();

    if (!name.isEmpty() && !phone.isEmpty()) {
        contacts.put(name, phone); // Add contact to the contacts map
        groups.computeIfAbsent(group, k -> new ArrayList<>()).add(name); // Add contact to the specified group
        contactArea.append(name + " - " + phone + " (Group: " + group + ")\n");

        // Debugging log to ensure employees are added to the group
        System.out.println("Contact added: " + name + ", Phone: " + phone + ", Group: " + group);
    } else {
        JOptionPane.showMessageDialog(this, "Name and Phone cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


        private void deleteContact() {
            String name = nameField.getText().trim();
            if (contacts.containsKey(name)) {
                contacts.remove(name);
                groups.values().forEach(list -> list.remove(name));
                displayAllContacts();
            } else {
                JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void searchContact() {
            String name = nameField.getText().trim();
            if (contacts.containsKey(name)) {
                String phone = contacts.get(name);
                contactArea.setText("Search Result:\n" + name + " - " + phone + "\n");
            } else {
                contactArea.setText("No contact found for: " + name + "\n");
            }
        }

        private void updateContact() {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            if (contacts.containsKey(name)) {
                contacts.put(name, phone);
                displayAllContacts();
            } else {
                JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void displayAllContacts() {
            contactArea.setText("All Contacts:\n");
            contacts.forEach((name, phone) -> {
                String group = groups.entrySet().stream()
                    .filter(entry -> entry.getValue().contains(name))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("No Group");
                contactArea.append(name + " - " + phone + " (Group: " + group + ")\n");
            });
        }

        private void addGroup() {
            String group = groupField.getText().trim();
            if (!group.isEmpty() && !groups.containsKey(group)) {
                groups.put(group, new ArrayList<>());
                JOptionPane.showMessageDialog(this, "Group added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Group already exists or name is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void deleteGroup() {
            String group = groupField.getText().trim();
            if (groups.containsKey(group)) {
                groups.remove(group);
                JOptionPane.showMessageDialog(this, "Group deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayAllContacts();
            } else {
                JOptionPane.showMessageDialog(this, "Group not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Payroll Code
	class PayrollSystem extends JFrame {
    private JTextField txtEmployeeID, txtName, txtHourlyRate, txtHoursWorked;
    private DefaultTableModel tableModel;
    private JTable table;

    public PayrollSystem() {
        setTitle("Payroll System");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the menu bar and menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem calculatorItem = new JMenuItem("Open Calculator");
        JMenuItem phonebookItem = new JMenuItem("View Phonebook Details");
        JMenuItem calculatePayrollItem = new JMenuItem("Calculate Payroll Using Calculator");

        // Add action listeners to menu items
        calculatorItem.addActionListener(e -> new Calculator().setVisible(true));
        phonebookItem.addActionListener(e -> displayEmployeeDetails());
        calculatePayrollItem.addActionListener(e -> openCalculatorForPayroll());

        // Add items to the menu
        menu.add(calculatorItem);
        menu.add(phonebookItem);
        menu.add(calculatePayrollItem);

        // Add menu to the menu bar and set it
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Create input panel for adding employees
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Employee ID:"));
        txtEmployeeID = new JTextField();
        inputPanel.add(txtEmployeeID);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Hourly Rate:"));
        txtHourlyRate = new JTextField();
        inputPanel.add(txtHourlyRate);

        inputPanel.add(new JLabel("Hours Worked:"));
        txtHoursWorked = new JTextField();
        inputPanel.add(txtHoursWorked);

        JButton btnAddEmployee = new JButton("Add Employee");
        btnAddEmployee.addActionListener(e -> addEmployee());
        inputPanel.add(btnAddEmployee);

        // Create a table for displaying employee records
        tableModel = new DefaultTableModel(
            new String[]{"Employee ID", "Name", "Hourly Rate", "Hours Worked", "Gross Pay", "Net Pay"}, 
            0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add input panel to the frame
        add(inputPanel, BorderLayout.NORTH);
    }

    private void addEmployee() {
        String id = txtEmployeeID.getText();
        String name = txtName.getText();
        String hourlyRateStr = txtHourlyRate.getText();
        String hoursWorkedStr = txtHoursWorked.getText();

        if (id.isEmpty() || name.isEmpty() || hourlyRateStr.isEmpty() || hoursWorkedStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contacts.containsKey(name)) {
            JOptionPane.showMessageDialog(this, "Employee must exist in the Phonebook.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double hourlyRate = Double.parseDouble(hourlyRateStr);
            double hoursWorked = Double.parseDouble(hoursWorkedStr);
            double grossPay = hourlyRate * hoursWorked;
            double netPay = grossPay * 0.8; // Assume a 20% deduction for taxes

            tableModel.addRow(new Object[]{id, name, hourlyRate, hoursWorked, grossPay, netPay});
            JOptionPane.showMessageDialog(this, "Employee added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void displayEmployeeDetails() {
    // Debugging: Print groups and contacts for verification
    System.out.println("Groups: " + groups);
    System.out.println("Contacts: " + contacts);

    // Retrieve employees in the "Employees" group
    StringBuilder details = new StringBuilder("Employee Details:\n");
    java.util.List<String> employees = groups.getOrDefault("Employees", Collections.emptyList());

    if (employees.isEmpty()) {
        details.append("No employees found in the Phonebook.\n");
    } else {
        for (String name : employees) {
            String phone = contacts.getOrDefault(name, "No phone number available");
            details.append("Name: ").append(name).append("\n")
                   .append("Phone: ").append(phone).append("\n\n");
        }
    }

    JOptionPane.showMessageDialog(this, details.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
}




    private void openCalculatorForPayroll() {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
        JOptionPane.showMessageDialog(this, "Use the Calculator to compute payroll manually.", "Calculator Mode", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}