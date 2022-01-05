import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class GameOver extends MouseAdapter {

    private Game game;
    private HUD hud;
    private Countdown countdown;
    private Menu menu;
    private Handler handler;
    private File file;
    private Level1 level1;
    private boolean appended = false;
    private boolean newHighscore = true;

    public GameOver(Game game, HUD hud, Countdown countdown, Menu menu, Handler handler, File file, Level1 level1){
        this.game = game;
        this.hud = hud;
        this.countdown = countdown;
        this.menu = menu;
        this.handler = handler;
        this.file = file;
        this.level1 = level1;
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
        Color color = new Color(186, 0, 196);
        Color color3 = new Color(118, 0, 196);
        Color color2 = new Color(68, 68, 68, 3);
        Color color4 = new Color(10, 203, 5);

        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(140f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font pokemonFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(30f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font pokemonFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(35f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        g.setColor(color2);
        g.fillRect(0,0,1920,1080);
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

        g.setColor(Color.black);
        g.setFont(buttonFont);
        g.drawString("Home", 700, 690);
        //g.drawRect(698, 645, 130, 53);

        //HIGHSCORE SETUP
        int highScore = hud.getEATSCORE();
        int oldHighscore = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null){
            int score = Integer.parseInt(line.trim());   // parse each line as an int
            if (score > highScore){
                highScore = score;
                newHighscore = false;
                oldHighscore = score;
            }

            line = reader.readLine();
        }
        reader.close();

        if (!appended && newHighscore) {
            //append highscore to the file
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.newLine();
            output.append("" + highScore);
            output.close();
            appended = true;
        }

        if(hud.getEATSCORE() == highScore ) {
            g.setColor(color4);
            g.setFont(pokemonFont3);
            g.drawString("New high score!", 655, 530);
        }else{
            g.setFont(pokemonFont3);
            g.drawString("Your highscore: " + oldHighscore, 640, 550);
        }
    }

}
