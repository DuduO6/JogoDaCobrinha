package src.main.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    private int finalScore;
    private JButton restartButton;

    public GameOverScreen(int finalScore, Runnable onRestart) {
        this.finalScore = finalScore;
        setLayout(new BorderLayout());

        JLabel message = new JLabel("Game Over", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 30));
        message.setForeground(Color.RED);

        JLabel scoreLabel = new JLabel("Final Score: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRestart.run();
            }
        });

        add(message, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);
    }
}
