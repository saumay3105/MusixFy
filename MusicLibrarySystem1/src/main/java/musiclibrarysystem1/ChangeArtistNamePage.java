package musiclibrarysystem1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangeArtistNamePage extends JFrame {

    private JTextField originalNameField;
    private JTextField newNameField;
    private JButton changeButton;

    public ChangeArtistNamePage() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Change Artist Name");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(43, 50, 56)); // Set background color

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(43, 50, 56)); // Set background color

        JLabel originalNameLabel = new JLabel("Original Artist Name:");
        originalNameLabel.setForeground(Color.WHITE); // Set text color
        inputPanel.add(originalNameLabel);

        originalNameField = new JTextField();
        inputPanel.add(originalNameField);

        JLabel newNameLabel = new JLabel("New Artist Name:");
        newNameLabel.setForeground(Color.WHITE); // Set text color
        inputPanel.add(newNameLabel);

        newNameField = new JTextField();
        inputPanel.add(newNameField);

        add(inputPanel, BorderLayout.CENTER);

        changeButton = new JButton("Change Name");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originalName = originalNameField.getText();
                String newName = newNameField.getText();
                if (changeArtistName(originalName, newName)) {
                    JOptionPane.showMessageDialog(ChangeArtistNamePage.this,
                            "Artist name updated successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ChangeArtistNamePage.this,
                            "Failed to update artist name. Artist not found or database error.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(changeButton);
        buttonPanel.setBackground(new Color(43, 50, 56)); // Set background color
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean changeArtistName(String originalName, String newName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", "root", "Neptune.1533");
            String query = "UPDATE artist SET name = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, originalName);
            int rowsAffected = statement.executeUpdate();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChangeArtistNamePage());
    }
}
