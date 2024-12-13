import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhonebookSystem extends JFrame {
    private JTextField nameField, phoneField;
    private JTextArea contactArea;
    private File contactFile = new File("contacts.txt");

    public PhonebookSystem() {
        // Initialize UI components
        setTitle("Phonebook System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Input fields
        nameField = new JTextField(15);
        phoneField = new JTextField(15);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);

        // Buttons
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton updateButton = new JButton("Update");
        JButton displayButton = new JButton("Display");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);

        // Text area for displaying contacts
        contactArea = new JTextArea(10, 40);
        contactArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(contactArea);

        // Add panels to the frame
        panel.add(inputPanel);
        panel.add(buttonPanel);
        panel.add(scrollPane);
        add(panel);

        // Button listeners
        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteContact());
        searchButton.addActionListener(e -> searchContact());
        updateButton.addActionListener(e -> updateContact());
        displayButton.addActionListener(e -> displayContacts());

        // Load contacts from file
        loadContacts();
    }

    private void loadContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
            String line;
            boolean isEmpty = true;
            while ((line = br.readLine()) != null) {
                contactArea.append(line + "\n");
                isEmpty = false;
            }
            if (isEmpty) {
                contactArea.append("No contacts found!\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading contacts: " + e.getMessage());
        }
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (!name.isEmpty() && !phone.isEmpty()) {
            String contact = name + "," + phone;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(contactFile, true))) {
                bw.write(contact);
                bw.newLine();
                contactArea.append(contact + "\n");
                nameField.setText("");
                phoneField.setText("");
            } catch (IOException e) {
                System.err.println("Error saving contact: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Name and Phone cannot be empty!");
        }
    }

    private void deleteContact() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            try {
                List<String> contacts = new ArrayList<>();
                boolean found = false;

                try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.startsWith(name + ",")) {
                            contacts.add(line);
                        } else {
                            found = true;
                        }
                    }
                }

                if (found) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(contactFile))) {
                        for (String contact : contacts) {
                            bw.write(contact);
                            bw.newLine();
                        }
                    }
                    contactArea.setText("");
                    for (String contact : contacts) {
                        contactArea.append(contact + "\n");
                    }
                    JOptionPane.showMessageDialog(this, "Contact deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Contact not found!");
                }
            } catch (IOException e) {
                System.err.println("Error deleting contact: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name to delete!");
        }
    }

    private void searchContact() {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
                String line;
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    if (line.toLowerCase().contains(name.toLowerCase())) {  // Case-insensitive search
                        contactArea.append("Found: " + line + "\n");
                        found = true;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "No contact found matching the name!");
                }
            } catch (IOException e) {
                System.err.println("Error searching contact: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name to search!");
        }
    }

    private void updateContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (!name.isEmpty() && !phone.isEmpty()) {
            try {
                List<String> contacts = new ArrayList<>();
                boolean found = false;

                // Read existing contacts and update the matching one
                try (BufferedReader br = new BufferedReader(new FileReader(contactFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith(name + ",")) {
                            contacts.add(name + "," + phone);  // Update contact
                            found = true;
                        } else {
                            contacts.add(line);  // Keep the original contact
                        }
                    }
                }

                if (found) {
                    // Write the updated list of contacts back to the file
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(contactFile))) {
                        for (String contact : contacts) {
                            bw.write(contact);
                            bw.newLine();
                        }
                    }
                    contactArea.setText("");
                    for (String contact : contacts) {
                        contactArea.append(contact + "\n");
                    }
                    JOptionPane.showMessageDialog(this, "Contact updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Contact not found!");
                }
            } catch (IOException e) {
                System.err.println("Error updating contact: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter both name and phone to update!");
        }
    }

    private void displayContacts() {
        contactArea.setText("");
        loadContacts();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhonebookSystem().setVisible(true));
    }
}