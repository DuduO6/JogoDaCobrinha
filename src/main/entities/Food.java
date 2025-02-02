package src.main.entities;
import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private int gridSize;
    private Random random;
    private Snake snake; // Referência para a cobra

    public Food(int gridSize, Snake snake) {
        this.gridSize = gridSize;
        this.snake = snake; // Armazena a referência à cobra
        this.random = new Random();
        spawn();
    }

    public void spawn() {
        do {
            int x = random.nextInt(gridSize);
            int y = random.nextInt(gridSize);
            position = new Point(x, y);
        } while (isOnSnake()); // Garante que a comida não nasça dentro da cobra
    }

    private boolean isOnSnake() {
        for (Point part : snake.getBody()) {
            if (part.equals(position)) {
                return true; // A comida foi gerada dentro da cobra
            }
        }
        return false;
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.RED);
        g.fillOval(position.x * tileSize, position.y * tileSize, tileSize, tileSize);
    }
}
