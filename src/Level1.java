import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Level1 {

    //classes
    private Countdown countdown;
    private Handler handler;
    private HUD hud;
    private BufferedImage koffing;
    private BufferedImage background;
    private BufferedImage grass;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private Game game;

    //variables
    public int timer = 0;
    public boolean level15Started;
    public boolean level15Reached;
    public int timerEnd = 20;
    public int eatscore;

    //constructor
    public Level1(Countdown countdown, Handler handler, HUD hud, BufferedImage koffing, MakeTransparent makeTransparent,
                  MakeMirror makeMirror, Game game, BufferedImage background, BufferedImage grass){
        this.countdown = countdown;
        this.handler = handler;
        this.hud = hud;
        this.koffing = koffing;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        this.game = game;
        this.background = background;
        this.grass = grass;
        this.eatscore = hud.getEATSCORE();
    }

    //tick method
    public void tick(){
        if (countdown.timer >= 260) {
            handler.tick();
            hud.tick();
            //later spawns
            timer++;
            eatscore = hud.getEATSCORE();
            if (level15Started) {
                timerEnd = 20;
                level15Reached = false;
                level15Started = false;
            }
            if (timer > timerEnd) {
                //add a koffing
                timer = 0;
                Random random = new Random();
                int randomX = random.nextInt();
                int randomY = random.nextInt();

                //check if spawn is in the player area
                if (randomX > (game.player.x - 200) || randomX < (game.player.x + 200)) {
                    randomX = (int) (game.player.x - 200);
                    if (randomX > 1400) {
                        randomX = 0;
                    } else if (randomX < 0) {
                        randomX = 1400;
                    }
                }
                if (randomY > (game.player.y - 200) || randomY < (game.player.y + 200)) {
                    randomY = (int) (game.player.y - 200);
                    if (randomY > 1400) {
                        randomY = 0;
                    } else if (randomY < 0) {
                        randomY = 1400;
                    }
                }
                if (eatscore < 15 && !level15Reached || hud.getEATSCORE() == 14) {
                    handler.addObject(new Koffing(randomX, randomY, ID.Koffing, handler, koffing, makeTransparent, makeMirror));
                }
                if (eatscore >= 15) {
                    if (level15Reached) {
                        level15Started = true;
                    }
                    handler.addObject(new Koffing(randomX, randomY, ID.Koffing, handler, koffing, makeTransparent, makeMirror));
                }

                if (eatscore == 14) {
                    level15Reached = true;
                } else if (eatscore >= 15 && level15Reached) {
                    handler.object = handler.getListWithoutEnemies(handler.object);
                    level15Reached = false;
                }
            }
        } else {
            countdown.tick();
        }
    }

    //render method
    public void render(Graphics g) throws IOException, FontFormatException {
        g.drawImage(background, 0, 0, null);
        Graphics2D g2d = (Graphics2D) g;

        if (countdown.timer >= 260) {
            game.inGame = true;
            handler.paintComponent(g);
            hud.render(g, g2d);

            //render ground
            int colour = grass.getRGB(0, 0);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 0, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 200, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 230, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 400, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 500, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 550, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 750, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 865, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 953, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1000, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1200, 750, null);
            g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1400, 750, null);
        } else {
            countdown.render(g);
        }
    }
}
