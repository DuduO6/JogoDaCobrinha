package src.main.entities;
import java.awt.*;
import java.util.Random;

public class Food {
    protected Point position;
    protected int gridSize;
    protected Random random;
    protected Snake snake;
    private boolean isSpecial;

    public Food(int gridSize, Snake snake, boolean isSpecial) {
        this.gridSize = gridSize;
        this.snake = snake;
        this.isSpecial = isSpecial;
        this.random = new Random();
        spawn();
    }

    public void spawn() {
        do {
            int x = random.nextInt(gridSize);
            int y = random.nextInt(gridSize);
            position = new Point(x, y);
        } while (isOnSnake());
    }

    private boolean isOnSnake() {
        for (Point part : snake.getBody()) {
            if (part.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g, int tileSize) {
        if (isSpecial) {
            g.setColor(Color.BLUE); // Comida especial azul
        } else {
            g.setColor(Color.RED); // Comida normal vermelha
        }
        g.fillOval(position.x * tileSize, position.y * tileSize, tileSize, tileSize);
    }
}
