package musiclibrarysystem1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

interface UserInterface {
    void customizeUI();
}

public class UserHomePage extends JFrame implements UserInterface {

    private JTextField songNameTextField;
    private JLabel statusLabel;
    private JButton playButton;
    private JButton pauseButton;
    private boolean isPlaying = false;

    public UserHomePage() {
        customizeUI();
    }

    @Override
    public void customizeUI() {
        getContentPane().setBackground(new Color(0, 0, 0));

        // Header label
        JLabel headerLabel = new JLabel("MusixFy");
        headerLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 36));
        headerLabel.setForeground(new Color(255, 105, 180)); // Pink color
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(100, 20, 400, 50);
        getContentPane().add(headerLabel);

        // Label for browsing songs
        JLabel browseLabel = new JLabel("Browse Songs:");
        browseLabel.setForeground(Color.BLACK);
        browseLabel.setBounds(50, 100, 100, 30);
        getContentPane().add(browseLabel);

        // Text field for song name
        songNameTextField = new JTextField();
        songNameTextField.setBounds(150, 100, 200, 30);
        getContentPane().add(songNameTextField);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome User");
        welcomeLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 24));
        welcomeLabel.setForeground(new Color(255, 105, 180)); // Pink color
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(150, 150, 200, 30);
        getContentPane().add(welcomeLabel);

        // Status label
        statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setBounds(50, 200, 400, 50);
        getContentPane().add(statusLabel);

        // Play button
        playButton = new JButton("Play");
        playButton.setBounds(100, 270, 100, 30);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String songName = songNameTextField.getText();
                boolean songExists = checkSongExists(songName);
                if (songExists) {
                    statusLabel.setText("Status: Playing " + songName);
                    isPlaying = true;
                } else {
                    JOptionPane.showMessageDialog(UserHomePage.this, "Song not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getContentPane().add(playButton);

        // Pause button
        pauseButton = new JButton("");
        pauseButton.setBounds(250, 270, 100, 30);
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPlaying) {
                    statusLabel.setText("Status: Paused " + songNameTextField.getText());
                    isPlaying = false;
                } else {
                    statusLabel.setText("Status: Already Paused " + songNameTextField.getText());
                }
            }
        });
        getContentPane().add(pauseButton);

        setPreferredSize(new Dimension(576, 576));
        pack();
    }

    private boolean checkSongExists(String songName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/music", "root", "Neptune.1533");
            String sql = "SELECT * FROM songs WHERE song_name=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, songName);
            ResultSet resultSet = statement.executeQuery();
            boolean exists = resultSet.next();
            connection.close();
            return exists;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(UserHomePage.this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserHomePage().setVisible(true);
            }
        });
    }
}
