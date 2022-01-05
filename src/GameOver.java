import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class GameOver extends MouseAdapter {

    //classes
    private Game game;
    private HUD hud;
    private Countdown countdown;
    private Menu menu;
    private Handler handler;
    private File fileLevel1;
    private File fileLevel2;
    private File fileLevel3;
    private Level1 level1;
    private HighscoreCheck highscoreCheck;

    //variables


    //constructor
    public GameOver(Game game, HUD hud, Countdown countdown, Menu menu, Handler handler, File fileLevel1, Level1 level1, HighscoreCheck highscoreCheck, File fileLevel2, File fileLevel3){
        this.game = game;
        this.hud = hud;
        this.countdown = countdown;
        this.menu = menu;
        this.handler = handler;
        this.fileLevel1 = fileLevel1;
        this.level1 = level1;
        this.highscoreCheck = highscoreCheck;
        this.fileLevel2 = fileLevel2;
        this.fileLevel3 = fileLevel3;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //home button
        if (mouseOver(mx, my, 698, 645, 130, 53) && game.gameState == Game.STATE.GameOver){
            game.inGame = false;
            game.gameState = Game.STATE.Menu;
            hud.resetHUD();
            countdown.resetData();
            game.gameover = false;
            menu.explosion = false;
            game.removedAllObjects = false;
            level1.timer = 0;
            level1.level15Reached = false;
            level1.level15Started = false;
            level1.timerEnd = 100;

            //remove all enemies
            handler.object = handler.getListWithoutEnemies(handler.object);

            try {
                game.gameoverAudio.stopMusic();
                game.foodAudio.stopMusic();
                game.mainAudio.startMusic();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

        public void tick(){

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

    public void render(Graphics g) throws IOException, FontFormatException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        //colors
        Color color = new Color(186, 0, 196);
        Color color3 = new Color(118, 0, 196);
        Color color2 = new Color(68, 68, 68, 3);

        //fonts
        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(140f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font pokemonFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(30f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        //opacitive background
        g.setColor(color2);
        g.fillRect(0,0,1920,1080);

        //text
        g.setFont(pokemonFont);
        g.setColor(color);
        if (hud.getEATSCORE() < 25) {
            g.drawString("Game over", 480, 400);
        }else{
            g.setColor(Color.green);
            g.drawString("Level completed!", 269, 400);
        }
        g.setColor(Color.black);
        g.setFont(pokemonFont2);
        g.drawString("Your final eating score: " + hud.getEATSCORE(), 605, 480);

        //home button
        g.setColor(Color.black);
        g.setFont(buttonFont);
        g.drawString("Home", 700, 690);
        //g.drawRect(698, 645, 130, 53);

        //highscore check and save based on levelstate
        if (game.levelState == Game.STATE.Level1) {
            highscoreCheck.render(g, fileLevel1);
        }else if (game.levelState == Game.STATE.Level2) {
            highscoreCheck.render(g, fileLevel2);
        }
    }

}
