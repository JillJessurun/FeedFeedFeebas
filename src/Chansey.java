import java.awt.*;

public class Chansey extends GameObject{
    private Handler handler;

    public Chansey(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velX = 2;
        velY = 1;
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
        g.setColor(Color.pink);
        g.fillRect((int) x, (int) y, 20, 20);
    }
}
