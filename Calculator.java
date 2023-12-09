import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JButton calculateButton;
    private JLabel resultLabel;

    public Calculator() {
        setTitle("Improved Student Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Calculate button
        calculateButton = new JButton("Calculate Grades");
        add(calculateButton, BorderLayout.SOUTH);

        // Result label
        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        // Action listener for the Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void calculateGrade() {
        int numSubjects = 5;
        int totalMarks = 0;

        for (int i = 0; i < numSubjects; i++) {
            try {
                String input = JOptionPane.showInputDialog("Enter marks for Subject " + (i + 1) + ":");
                int marks = Integer.parseInt(input);
                totalMarks += marks;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid marks for all subjects.");
                return;
            }
        }

        double averagePercentage = (double) totalMarks / numSubjects;

        // Determine letter grade
        char letterGrade;
        if (averagePercentage >= 90) {
            letterGrade = 'A';
        } else if (averagePercentage >= 80) {
            letterGrade = 'B';
        } else if (averagePercentage >= 70) {
            letterGrade = 'C';
        } else if (averagePercentage >= 60) {
            letterGrade = 'D';
        } else {
            letterGrade = 'F';
        }

        // Display results
        String resultText = String.format("Total Marks: %d, Average Percentage: %.2f%%, Grade: %c", totalMarks, averagePercentage, letterGrade);
        resultLabel.setText(resultText);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
