import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Player extends GameObject{

    private Handler handler;
    private HUD hud;
    private KeyInput keyInput;
    private Random random;
    private BufferedImage feebas;
    private BufferedImage food;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private Game game;

    private int positiveVelocity = 7;
    private int negativeVelocity = -7;
    private boolean voltorbHit = false;
    private boolean glalieHit = false;
    private int timer = 150;
    private int timer2 = 70;
    public boolean right = false;
    public boolean left = false;
    public boolean foodEaten = false;

    //gifs enemy attacks
    private Image electricity;
    private Image ice;
    private Image poison;

    public Player(float x, float y, ID id, Handler handler, HUD hud, KeyInput keyInput, BufferedImage feebas, MakeTransparent makeTransparent, BufferedImage food, MakeMirror makeMirror, Game game) {
        super(x, y, id);
        this.handler = handler;
        this.food = food;
        this.hud = hud;
        this.keyInput = keyInput;
        this.keyInput = new KeyInput(handler, this, game);
        this.feebas = feebas;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        this.game = game;
        random = new Random();

        electricity = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\electricity.gif");
        ice = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\ice.gif");
        poison = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\poison.gif");
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, -60, Game.WIDTH - 130);
        y = Game.clamp(y, -50, Game.HEIGHT - 138);

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
        Graphics2D g2d = (Graphics2D) g;
        int colour = feebas.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(feebas, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        if (right && !left){
            g2d.drawImage(makeMirror.Mirror(transparent), (int) x - 115, (int) y + 27, null);
            if (voltorbHit) {
                g.drawImage(electricity, (int) x + 60, (int) y + 50, null);
            }
            if (glalieHit) {
                g.drawImage(ice, (int) x + 60, (int) y + 50, null);
            }
            if (hud.koffingHit) {
                g.drawImage(poison, (int) x + 60, (int) y + 50, null);
            }
        }else{
            g2d.drawImage(transparent, (int) x + 25, (int) y + 25, null);
            if (voltorbHit) {
                g.drawImage(electricity, (int) x + 60, (int) y + 50, null);
            }
            if (glalieHit) {
                g.drawImage(ice, (int) x + 60, (int) y + 50, null);
            }
            if (hud.koffingHit) {
                g.drawImage(poison, (int) x + 60, (int) y + 50, null);
            }
        }
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
                        if (hud.getCHANSEYRATE() == 1000) {
                            hud.setCHANSEYRATE(0);
                            hud.setHEALTH(0);
                        }
                    }
                }else if (tempObject.getId() == ID.Food) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        hud.setHEALTH(HUD.HEALTH - 800);
                        hud.setEATSCORE(HUD.EATSCORE + 1);
                        handler.removeObject(tempObject);
                        foodEaten = true;

                        Food foodClass = new Food(random.nextInt(0,Game.WIDTH - 80), random.nextInt(0,Game.HEIGHT- 80), ID.Food, handler, makeTransparent, food, this, game);

                        handler.addObject(foodClass);
                    }
                }
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle ((int)x+60, (int)y+48, 70, 92);
    }

    public int getPositiveVelocity() {
        return positiveVelocity;
    }

    public int getNegativeVelocity() {
        return negativeVelocity;
    }
}
