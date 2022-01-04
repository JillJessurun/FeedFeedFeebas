import java.awt.*;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Menu extends MouseAdapter {

    private BufferedImage feebasBG;
    private BufferedImage feebasSprite;
    private BufferedImage feebasMoving;
    private BufferedImage feebasMoving2;
    private BufferedImage food;
    private MakeTransparent makeTransparent;
    private Handler handler;
    private Game game;
    private Random random;
    private Image image;
    public boolean explosion = false;
    private int timer = 0;
    private int timerAudio = 0;
    public boolean playPressed = false;
    private Countdown countdown;
    private boolean gameoverAudioStarted = false;
    private int timer2 = 0;

    private float y = 370;
    private float x = 1470;

    private float y2 = 430;
    private float x2 = 1470;

    public Menu(BufferedImage feebasBG, BufferedImage feebasSprite, MakeTransparent makeTransparent,
                Handler handler, Game game, BufferedImage feebasMoving, Random random, BufferedImage food, Countdown countdown){
        this.feebasBG = feebasBG;
        this.feebasSprite = feebasSprite;
        this.makeTransparent = makeTransparent;
        this.handler = handler;
        this.game = game;
        this.feebasMoving = feebasMoving;
        this.feebasMoving2 = feebasMoving;
        this.random = random;
        this.food = food;
        this.countdown = countdown;

        image = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\explosion.gif");
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        //play button
        if (mouseOver(mx, my, 238, 325, 105, 53) && game.gameState == Game.STATE.Menu){
            game.gameState = Game.STATE.Level1;

            Image newimage = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\steak.gif");
            Image newimage2 = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\loading.gif");
            countdown.setImage3(newimage);
            countdown.setImage2(newimage2);

            playPressed = true;
            game.gameStarted = true;
            try {
                game.mainAudio.stopMusic();
                game.loadingAudio.startMusic();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //options button
        if (mouseOver(mx, my, 238, 485, 180, 53) && game.gameState == Game.STATE.Menu){
            game.gameState = Game.STATE.Options;
        }

        //quit button
        if (mouseOver(mx, my, 238, 645, 110, 53) && game.gameState == Game.STATE.Menu){
            //System.exit(0);
            game.gameState = Game.STATE.PopUp;
        }

        //back button options
        if (mouseOver(mx, my, 238, 645, 110, 53) && game.gameState == Game.STATE.Options){
            game.gameState = Game.STATE.Menu;
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if (mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void stopIngameAudio(){
        Game.level1Audio.stopMusic();
    }

    public void stopLoadingAudio(){
        Game.loadingAudio.stopMusic();
    }

    public void stopGameoverAudio(){
        Game.gameoverAudio.stopMusic();
    }

    public void stopFoodAudio(){
        Game.foodAudio.stopMusic();
    }

    public void tick(){
        if (explosion){
            timer++;
            if (gameoverAudioStarted) {
                gameoverAudioStarted = false;
            }
        }
        if (timer > 110){
            timer = 0;
            explosion = false;
        }

        if (x > 840) {
            x = x - 5.5f;
        }else{
            if (y >= 370 && y < 420){
                x = x - 2f;
                y = y + 4;
            }else{
                x = x - 1;
                y = y + 10;
            }
        }
        if (y > 1000){
            y = 370;
            x = 1470;
            y2 = 430;
            x2 = 1470;
            explosion = false;
            gameoverAudioStarted = true;
        }else if (y > 700){
            explosion = true;
        }

        if (x2 > 860) {
            x2 = x2 - 6.1f;
        }else{
            if (y2 >= 370 && y2 < 420){
                x2 = x2 - 2f;
                y2 = y2 + 4;
            }else{
                x2 = x2 - 1;
                y2 = y2 + 10;
            }
        }
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        g.drawImage(feebasBG, 0,0,null);
        g.setColor(Color.BLACK);

        Color color = new Color(0, 112, 183);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\The Warrior.otf")).deriveFont(140f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\The Warrior.otf")));

        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        //title
        g.setFont(titleFont);
        g.setColor(color);
        g.drawString("Feed Feebas!", 140, 190);

        //big feebas pic
        int colour = feebasSprite.getRGB(0, 0);
        g.drawImage(makeTransparent.makeColorTransparent(feebasSprite, new Color(colour)), 900, -60, null);

        //buttons
        g.setFont(buttonFont);
        g.setColor(Color.black);
        if (game.gameState == Game.STATE.Menu || game.gameState == Game.STATE.PopUp) {
            g.drawString("Play", 240, 370);
            //g.drawRect(238, 325, 105, 53);
            g.drawString("Options", 240, 530);
            //g.drawRect(238, 485, 180, 53);
            g.drawString("Quit", 240, 690);
            //g.drawRect(238, 645, 110, 53);
        }else if (game.gameState == Game.STATE.Options){
            g.drawString("Back", 240, 690);
        }

        //suicide feebas
        g.drawImage(makeTransparent.makeColorTransparent(feebasMoving, new Color(colour)), (int)x, (int)y, null);
        g.drawImage(makeTransparent.makeColorTransparent(food, new Color(colour)), (int)x2, (int)y2, null);

        //gif
        if (explosion) {
            g.drawImage(image, 630, 465, null);
        }
    }
}
