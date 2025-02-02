package src.main.entities;
import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body; // Lista de pontos representando o corpo da cobra
    private Direction direction; // Direção atual da cobra
    private boolean isGrowing = false;
    private int gridSize;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Snake(int startX, int startY, int gridSize) {
        this.gridSize = gridSize; // Armazena o tamanho do grid
        body = new LinkedList<>();
        body.add(new Point(startX, startY)); // Posição inicial da cobra
        direction = Direction.RIGHT; // Começa indo para a direita
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);
    
        switch (direction) {
            case UP -> newHead.y -= 1;
            case DOWN -> newHead.y += 1;
            case LEFT -> newHead.x -= 1;
            case RIGHT -> newHead.x += 1;
        }
    
        // Teletransporte se a cobra sair dos limites
        if (newHead.x < 0) {
            newHead.x = gridSize - 1; // Sai pela esquerda e entra pela direita
        } else if (newHead.x >= gridSize) {
            newHead.x = 0; // Sai pela direita e entra pela esquerda
        }
    
        if (newHead.y < 0) {
            newHead.y = gridSize - 1; // Sai pela parte superior e entra pela inferior
        } else if (newHead.y >= gridSize) {
            newHead.y = 0; // Sai pela parte inferior e entra pela superior
        }
    
        body.addFirst(newHead); // Adiciona a nova cabeça
    
        // Remove o último segmento apenas se a cobra não comeu a comida
        if (!isGrowing) {
            body.removeLast(); // Remove o último segmento para manter o tamanho
        } else {
            isGrowing = false; // Reseta a flag de crescimento
        }
    }

    public void grow() {
        isGrowing = true; // Define que a cobra deve crescer na próxima movimentação
    }
    

    public void setDirection(Direction newDirection) {
        // Evita que a cobra faça um movimento de 180 graus
        if ((this.direction == Direction.UP && newDirection != Direction.DOWN) ||
            (this.direction == Direction.DOWN && newDirection != Direction.UP) ||
            (this.direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            this.direction = newDirection;
        }
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public boolean checkCollision() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true; // Colisão com o próprio corpo
            }
        }
        return false;
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x * tileSize, p.y * tileSize, tileSize, tileSize);
        }
    }

    public boolean eat(Food food) {
        if (getHead().equals(food.getPosition())) {
            grow();
            return true;
        }
        return false;
    }
    
    public boolean collides() {
        return checkCollision(); // Usa o método já existente
    }
     
}
