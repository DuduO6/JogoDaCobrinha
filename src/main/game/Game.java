package src.main.game;

import javax.swing.*;

public class Game extends JFrame {
    private String playerName;
    private Board board;

    public Game() {
        setTitle("Jogo da Cobrinha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Inicia em tela cheia
        setUndecorated(true); // Remove a barra de título

        playerName = JOptionPane.showInputDialog("Digite seu nome:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Jogador";
        }

        board = new Board(playerName);
        add(board);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
