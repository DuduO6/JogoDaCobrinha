package src.main.game;
import java.awt.*;

public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public void increase() {
        score += 10; // Aumenta 10 pontos por comida
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }

    public void draw(Graphics g, int width) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
    }
}
