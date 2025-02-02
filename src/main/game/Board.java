package src.main.game;
import javax.swing.*;

import src.main.entities.Food;
import src.main.entities.Snake;
import src.main.entities.Snake.Direction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private final int TILE_SIZE = 20;
    private final int GRID_SIZE = 20;
    private final int DELAY = 150;

    private Snake snake;
    private Food food;
    private Timer timer;
    private boolean running;
    private Score score; // Adiciona o atributo score


    public Board() {
        setPreferredSize(new Dimension(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> snake.setDirection(Snake.Direction.UP);
                    case KeyEvent.VK_DOWN -> snake.setDirection(Snake.Direction.DOWN);
                    case KeyEvent.VK_LEFT -> snake.setDirection(Snake.Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> snake.setDirection(Snake.Direction.RIGHT);
                }
            }
        });

        startGame();
    }

    private void startGame() {
        snake = new Snake(GRID_SIZE / 2, GRID_SIZE / 2, GRID_SIZE); // Adicione o gridSize aqui
        food = new Food(GRID_SIZE, snake);
        score = new Score(); // Inicializa a pontuação
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            food.draw(g, TILE_SIZE);
            drawSnake(g);
        } else {
            showGameOver(g);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : snake.getBody()) {
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void showGameOver(Graphics g) {
        String message = "Game Over";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(message, (getWidth() - metrics.stringWidth(message)) / 2, getHeight() / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move();
            checkFoodCollision();
            checkSelfCollision();
        }
        repaint();
    }

    private void checkSelfCollision() {
        if (snake.checkCollision()) {
            running = false;
            timer.stop();
            showGameOverScreen();  // Exibe a tela de Game Over
        }
    }

    private void checkFoodCollision() {
        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();  // Cresce ao comer a comida
            food.spawn();  // Gera uma nova posição para a comida
            score.increase(); // Aumenta a pontuação
        }
    }

    private void showGameOverScreen() {
        running = false;
        timer.stop();
        repaint();
        
        JOptionPane.showMessageDialog(this, "Game Over! Pontuação: " + score.getScore(), 
                                      "Game Over", JOptionPane.INFORMATION_MESSAGE);
        restartGame();
    }    

    private void restartGame() {
        snake = new Snake(GRID_SIZE / 2, GRID_SIZE / 2, GRID_SIZE); // Adicione o gridSize aqui
        food = new Food(GRID_SIZE, snake);
        score.reset();
        running = true;
        timer.start();
        repaint();
    }
    


}
