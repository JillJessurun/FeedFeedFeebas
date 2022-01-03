import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GameOver extends MouseAdapter {

    private Game game;
    private HUD hud;
    private Countdown countdown;

    public GameOver(Game game, HUD hud, Countdown countdown){
        this.game = game;
        this.hud = hud;
        this.countdown = countdown;
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

            try {
                game.ingameAudio.stopMusic();
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

        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(140f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font pokemonFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(30f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));
        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));

        g.setColor(color2);
        g.fillRect(0,0,1920,1080);
        g.setFont(pokemonFont);
        g.setColor(color);
        g.drawString("Game over", 480, 400);
        g.setColor(Color.black);
        g.setFont(pokemonFont2);
        g.drawString("Your final eating score: " + hud.getEATSCORE(), 605, 480);

        g.setColor(Color.black);
        g.setFont(buttonFont);
        g.drawString("Home", 700, 690);
        //g.drawRect(698, 645, 130, 53);
    }

}
