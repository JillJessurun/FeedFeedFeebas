import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;

public class Player extends GameObject{

    private Handler handler;

    public Player(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 32);
        y = Game.clamp(y, 0, Game.HEIGHT - 32);

        collision();
    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect((int)x, (int)y, 32, 32);
    }

    private void collision(){
        for (int i = 0; i < handler.object.size(); i++) {
            if (this.getId() == ID.Player) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.Voltorb) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        HUD.HEALTH += 1;
                        HUD.HEALTHPERCENTAGE = HUD.HEALTH / 100;
                    }
                }
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle ((int)x, (int)y, 32, 32);
    }
}
