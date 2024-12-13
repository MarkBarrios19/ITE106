import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String num1 = "", num2 = "", operator = "";
    private boolean isNewOperation = true;  // Flag to determine if we need to start a new number input

    public Calculator() {
        setTitle("Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));  // Add space between buttons

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (isNewOperation) {
                display.setText(command);  // Start a new number
                isNewOperation = false;  // We're no longer in a state for new operation
            } else {
                display.setText(display.getText() + command);  // Continue appending digits
            }
        } else if ("/*-+".contains(command)) {
            if (operator.isEmpty()) {
                num1 = display.getText();
                operator = command;
                isNewOperation = true;  // After entering an operator, the next input should be a new number
            }
        } else if ("=".equals(command)) {
            num2 = display.getText();
            try {
                double result = calculate(Double.parseDouble(num1), Double.parseDouble(num2), operator);
                display.setText(String.valueOf(result));
                saveToHistory(num1, operator, num2, result);
                isNewOperation = true;  // Set flag to start a new calculation after '='
            } catch (ArithmeticException ex) {
                display.setText("Error: Div by Zero");
                isNewOperation = true;  // Allow retry after an error
            } catch (Exception ex) {
                display.setText("Error");
                isNewOperation = true;  // Allow retry after an error
            }
        } else if ("C".equals(command)) {
            display.setText("");
            num1 = num2 = operator = "";
            isNewOperation = true;  // Reset all when 'C' is pressed
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) throw new ArithmeticException("Cannot divide by zero");
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    private void saveToHistory(String num1, String operator, String num2, double result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt", true))) {
            writer.write(num1 + " " + operator + " " + num2 + " = " + result + "\n");
        } catch (IOException e) {
            System.out.println("Error saving history: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}