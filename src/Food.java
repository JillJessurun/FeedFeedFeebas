//different kinds of food? make an array with different food types, use Random to pick a random food
//want different characteristics? example: if array index 0 (lets say meat) -> then take more hunger away

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Food extends GameObject{
    private Handler handler;
    private Random random;
    private BufferedImage food;
    private MakeTransparent makeTransparent;
    private Player player;
    private Game game;
    public int timer = 0;

    public Food(float x, float y, ID id, Handler handler, MakeTransparent makeTransparent, BufferedImage food, Player player, Game game) {
        super(x, y, id);
        this.handler = handler;
        this.makeTransparent = makeTransparent;
        this.food = food;
        this.player = player;
        this.game = game;
        random = new Random();

        velX = random.nextFloat(-1, 1);
        velY = random.nextFloat(-1, 1);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (player.foodEaten){
            if (!game.foodAudio.hasStarted) {
                try {
                    game.foodAudio.startMusic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{
                try {
                    game.foodAudio.stopMusic();
                    game.foodAudio.startMusic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            player.foodEaten = false;
        }

        timer++;

        if (timer >= 80){
            timer = 0;
            try {
                if (game.foodAudio.hasStarted) {
                    game.foodAudio.stopMusic();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (y <= -15 || y >= Game.HEIGHT - 93){
            velY *= -1;
        }
        if (x <= -10 || x >= Game.WIDTH - 53) {
            velX *= -1;
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int colour = food.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(food, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        g2d.drawImage(transparent, (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x + 8, (int) y + 10, 50, 45);
    }
}
