import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject{

    private Handler handler;
    private HUD hud;
    private KeyInput keyInput;
    private Random random;

    private int positiveVelocity = 7;
    private int negativeVelocity = -7;
    private boolean voltorbHit = false;
    private boolean glalieHit = false;
    private int timer = 150;
    private int timer2 = 70;

    public Player(float x, float y, ID id, Handler handler, HUD hud, KeyInput keyInput) {
        super(x, y, id);
        this.handler = handler;
        this.hud = hud;
        this.keyInput = keyInput;
        this.keyInput = new KeyInput(handler, this);
        random = new Random();
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 32);
        y = Game.clamp(y, 0, Game.HEIGHT - 32);

        collision();

        if (voltorbHit){
            positiveVelocity = 2;
            negativeVelocity = -2;
            timer--;
            if (timer == 0){
                positiveVelocity = 7;
                negativeVelocity = -7;
                timer = 150;
                voltorbHit = false;
            }
        }
        if (glalieHit){
            positiveVelocity = 0;
            negativeVelocity = 0;
            timer2--;
            if (timer2 == 0){
                positiveVelocity = 7;
                negativeVelocity = -7;
                timer2 = 70;
                glalieHit = false;
                keyInput.glalieHit = false;
            }
        }
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
                        voltorbHit = true;
                    }
                }else if (tempObject.getId() == ID.Koffing) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        hud.koffingHit = true;
                    }
                }else if (tempObject.getId() == ID.Glalie) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        glalieHit = true;
                        keyInput.stopPlayer();
                        keyInput.glalieHit = true;
                    }
                }else if (tempObject.getId() == ID.Chansey) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        hud.setHEALTH(0);
                    }
                }else if (tempObject.getId() == ID.Food) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        hud.setHEALTH(HUD.HEALTH - 800);
                        hud.setEATSCORE(HUD.EATSCORE + 1);
                        handler.removeObject(tempObject);
                        handler.addObject(new Food(random.nextInt(0,Game.WIDTH - 20), random.nextInt(0,Game.HEIGHT- 20), ID.Food, handler));
                    }
                }
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle ((int)x, (int)y, 32, 32);
    }

    public int getPositiveVelocity() {
        return positiveVelocity;
    }

    public int getNegativeVelocity() {
        return negativeVelocity;
    }
}
