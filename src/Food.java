//different kinds of food? make an array with different food types, use Random to pick a random food
//want different characteristics? example: if array index 0 (lets say meat) -> then take more hunger away

import java.awt.*;
import java.util.Random;

public class Food extends GameObject{
    private Handler handler;
    private Random random;

    public Food(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        random = new Random();

        velX = random.nextFloat(0, 1);
        velY = random.nextFloat(0, 1);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 20, 20);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 20) {
            velY *= -1;
        }
        if (x <= 0 || x >= Game.WIDTH - 20) {
            velX *= -1;
        }
    }

    public void render(Graphics g) {
        Color color = new Color(82, 7, 9);
        g.setColor(color);
        g.fillRect((int) x, (int) y, 20, 20);
    }
}
