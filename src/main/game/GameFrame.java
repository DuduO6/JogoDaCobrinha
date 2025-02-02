package src.main.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    private JLabel scoreLabel;
    private int score;
    private Timer timer; // Timer para o jogo

    public GameFrame() {
        setTitle("Jogo da Cobrinha");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        scoreLabel = new JLabel("Pontuação: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel, BorderLayout.NORTH);

        // Inicializar o jogo
        startGame();

        setVisible(true); // Torna a janela visível
    }

    private void startGame() {
        score = 0; // Inicializa a pontuação
        updateScore(); // Atualiza a pontuação na tela

        // Inicia o timer para o jogo
        timer = new Timer(100, this); // Ajuste o intervalo conforme necessário
        timer.start();
    }

    public void updateScore() {
        scoreLabel.setText("Pontuação: " + score);
    }

    // Método para incrementar a pontuação
    public void incrementScore() {
        score++;
        updateScore();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Aqui você pode adicionar a lógica do jogo, como mover a cobra, verificar colisões, etc.
        // Exemplo: incrementScore(); // Chame isso quando a cobra comer a comida
    }
}