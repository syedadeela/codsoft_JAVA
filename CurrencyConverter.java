import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> baseCurrencyComboBox;
    private JComboBox<String> targetCurrencyComboBox;
    private JTextField amountField;
    private JLabel resultLabel;

    private static final String API_URL = "https://api.exchangeratesapi.io/latest";

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        baseCurrencyComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP"}); // Default base currencies
        targetCurrencyComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP"}); // Default target currencies
        amountField = new JTextField(10);
        resultLabel = new JLabel("Result: ");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Base Currency:"));
        inputPanel.add(baseCurrencyComboBox);
        inputPanel.add(new JLabel("Target Currency:"));
        inputPanel.add(targetCurrencyComboBox);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(createButton("Convert", e -> convertCurrency()));

        add(inputPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    private void convertCurrency() {
        String baseCurrency = (String) baseCurrencyComboBox.getSelectedItem();
        String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
            return;
        }

        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
        double convertedAmount = amount * exchangeRate;

        resultLabel.setText(String.format("Result: %.2f %s", convertedAmount, targetCurrency));
    }

    private double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(API_URL + "?base=" + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // Parsing the JSON response to get the exchange rate
            String jsonResponse = response.toString();
            String rateKey = "\"" + targetCurrency + "\":";
            int rateIndex = jsonResponse.indexOf(rateKey) + rateKey.length();
            int endIndex = jsonResponse.indexOf(",", rateIndex);
            String rateString = jsonResponse.substring(rateIndex, endIndex);
            return Double.parseDouble(rateString);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error fetching exchange rates. Please try again later.");
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
