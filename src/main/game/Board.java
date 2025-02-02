package src.main.game;

import src.main.entities.Snake;
import src.main.entities.Food;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Snake snake;
    private Food food;
    private int score;
    private boolean running;
    private String playerName;
    private static final int TILE_SIZE = 35; // Tamanho de cada quadrado
    private int gridSizeX;
    private int gridSizeY;
    private Food specialFood;
    private Random random = new Random();
    private static final String SCORE_FILE = "scores.txt";

    public Board(String playerName) {
        this.playerName = playerName;
    
        // Calculando quantas células cabem na tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gridSizeY = screenSize.height / TILE_SIZE;
        gridSizeX = screenSize.width / TILE_SIZE;
        
        setPreferredSize(screenSize);
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
    
        startGame();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) {
                    snake.setDirection(Snake.Direction.UP);
                } else if (key == KeyEvent.VK_DOWN) {
                    snake.setDirection(Snake.Direction.DOWN);
                } else if (key == KeyEvent.VK_LEFT) {
                    snake.setDirection(Snake.Direction.LEFT);
                } else if (key == KeyEvent.VK_RIGHT) {
                    snake.setDirection(Snake.Direction.RIGHT);
                }
            }
        });

        
    }

    private void startGame() {
        snake = new Snake(gridSizeX / 2, gridSizeY / 2, gridSizeX);
        food = new Food(gridSizeX, snake, false);
        if (random.nextInt(20) == 0) { // 20% de chance da comida especial aparecer
            specialFood = new Food(gridSizeX, snake, true);
        } else {
            specialFood = null;
        }
        score = 0;
        running = true;
        timer = new Timer(100, this);
        timer.start();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            int tileSize = 20; // Defina o tamanho de cada célula
            food.draw(g, tileSize);
            snake.draw(g, tileSize);

            g.setColor(Color.WHITE);
            g.drawString("Pontuação: " + score, 20, 20);
        } else {
            showGameOverScreen(g);
        }
    }

    private void showGameOverScreen(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("VOCE PERDEU", getWidth() / 2 - 100, getHeight() / 4);
        g.drawString("Pontuação: " + score, getWidth() / 2 - 100, getHeight() / 2 + 20);
        displayHighScores(g);
        saveScore();

    
        JButton exitButton = new JButton("Sair");
        exitButton.setBounds(getWidth() / 2 + 50, getHeight() / 2 + 100, 100, 40);
        exitButton.addActionListener(e -> System.exit(0));
    
        setLayout(null);
        add(exitButton);
        exitButton.setVisible(true);
    }

    private void saveScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(playerName + " - " + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayHighScores(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Pontuações:", getWidth() / 2 - 100, getHeight() / 2 + 100);
        
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int y = getHeight() / 2 + 130;
        for (String s : scores) {
            g.drawString(s, getWidth() / 2 - 100, y);
            y += 30;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move();
            
            // Comida normal
            if (snake.getHead().equals(food.getPosition())) {
                snake.grow();
                score++;
                food.spawn();
            }

            // Comida especial (faz crescer 2 vezes)
            if (specialFood != null && snake.getHead().equals(specialFood.getPosition())) {
                snake.grow();
                snake.grow();
                score += 2;
                specialFood = null; // Comida especial some depois de ser comida
            }

            // Adiciona nova comida especial aleatoriamente
            if (specialFood == null && random.nextInt(10) == 0) { 
                specialFood = new Food(gridSizeX, snake, true);
            }

            if (snake.checkCollision()) {
                running = false;
                timer.stop();
                repaint();
            }

            repaint();
        }
    }
}
