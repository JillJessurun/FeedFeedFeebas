import java.awt.*;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelMenu extends MouseAdapter {

    private Game game;
    private Countdown countdown;
    private Menu menu;
    private File file;
    public boolean playPressed;

    public LevelMenu(Game game, Countdown countdown, Menu menu, File file) {
        this.game = game;
        this.countdown = countdown;
        this.menu = menu;
        this.playPressed = menu.playPressed;
        this.file = file;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //back button level menu
        if (mouseOver(mx, my, 433,748, 126,60) && game.gameState == Game.STATE.LevelMenu) {
            game.gameState = Game.STATE.Menu;
        }

        //level1 button level menu
        if (mouseOver(mx, my, 257, 380, 80,22) && game.gameState == Game.STATE.LevelMenu) {
            game.gameState = Game.STATE.Level1;

            Image newimage = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\steak.gif");
            Image newimage2 = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\loading.gif");
            countdown.setImage3(newimage);
            countdown.setImage2(newimage2);

            menu.playPressed = true;
            game.gameStarted = true;
            try {
                game.mainAudio.stopMusic();
                game.loadingAudio.startMusic();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static float getMouseXposition(){
        Point point = MouseInfo.getPointerInfo().getLocation();
        return (float) point.getX();
    }

    public static float getMouseYposition(){
        Point point = MouseInfo.getPointerInfo().getLocation();
        return (float) point.getY();
    }

    public static boolean mouseHover(float x, float y, int width, int height){
        if (getMouseXposition() > (x + width)){
            return false;
        }else if (getMouseXposition() < x){
            return false;
        }else if (getMouseYposition() > (y + height)){
            return false;
        }else if (getMouseYposition() < y){
            return false;
        }else{
            return true;
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void drawLevel(float x, float y, int width, int height, int drawX, int drawY, int level, Graphics g, File file) throws IOException {
        //read file and look for the highscore
        int highScore = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null){
            int score = Integer.parseInt(line.trim());   // parse each line as an int
            if (score > highScore){
                highScore = score;
            }
            line = reader.readLine();
        }
        reader.close();

        if (highScore >= 25 && level == 1){
            g.setColor(Color.green);
        }else {
            g.setColor(Color.black);
        }
        if (mouseHover(x, y, width,height)){
            g.setColor(Color.white);
        }
        g.drawString("  Level " + level, drawX, drawY);
    }

    public void tick() {

    }

    public void render(Graphics g) throws IOException, FontFormatException {
        Graphics2D g2d = (Graphics2D) g;

        //world font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font worldFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(35f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        //button font
        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\WreckedShip.ttf")).deriveFont(35f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\WreckedShip.ttf")));
        Font buttonFont2 = new Font("Comic Sans MS", Font.BOLD, 22);

        //back button font
        Font backButtonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        //colors
        Color color = new Color(0, 61, 98);
        Color color2 = new Color(199, 199, 199);

        //box
        g.setColor(color);
        g2d.setStroke(new BasicStroke(10));
        g.fillRect(200, 300, 600, 400);
        g.setColor(Color.black);
        g.drawRect(200, 300, 600, 400);

        //worlds
        g.setColor(Color.black);
        g.setFont(worldFont);
        g.drawString("World 1", 240, 360);
        g.setColor(Color.darkGray);
        g.drawString("World 2", 430, 360);
        g.drawString("World 3", 630, 360);
        g.drawString("World 4", 240, 540);
        g.drawString("World 5", 430, 540);
        g.drawString("World 6", 630, 540);

        //buttons
        //g.setColor(color2);
        g.setFont(buttonFont2);

        //world 1 levels
        drawLevel(257, 380, 80,22, 240, 400, 1, g, file);
        drawLevel(257, 410, 80,22, 240, 430, 2, g, file);
        drawLevel(257, 440, 80,22, 240, 460, 3, g, file);
        //world 2 levels
        g.setColor(Color.gray);
        g.drawString("  Level 4", 430, 400);
        g.drawString("  Level 5", 430, 430);
        g.drawString("  Level 6", 430, 460);
        //world 3 levels
        g.drawString("  Level 7", 630, 400);
        g.drawString("  Level 8", 630, 430);
        g.drawString("  Level 9", 630, 460);
        //world 4 levels
        g.drawString("  Level 10", 240, 580);
        g.drawString("  Level 11", 240, 610);
        g.drawString("  Level 12", 240, 640);
        //world 5 levels
        g.drawString("  Level 13", 430, 580);
        g.drawString("  Level 14", 430, 610);
        g.drawString("  Level 15", 430, 640);
        //world 6 levels
        g.drawString("  Level 16", 630, 580);
        g.drawString("  Level 17", 630, 610);
        g.drawString("  Level 18", 630, 640);

        g.setFont(backButtonFont);
        g.setColor(Color.black);
        g.drawString("Back", 440, 800);
    }
}
