import java.awt.*;

public class Koffing extends GameObject{

    private Handler handler;

    public Koffing(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velX = 3;
        velY = 2;
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
        Color color = new Color(124, 0, 176);
        g.setColor(color);
        g.fillRect((int) x, (int) y, 20, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 20, 20);
    }
}
