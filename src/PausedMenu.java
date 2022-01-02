import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class PausedMenu extends MouseAdapter {

    private Game game;
    private boolean inOptions = false;

    public PausedMenu(Game game){
        this.game = game;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        //continue button in game pause
        if (mouseOver(mx, my, 665, 365, 200, 53) && game.gameState == Game.STATE.Pause){
            game.gameState = Game.STATE.Level1;
        }

        //options button in game pause
        if (mouseOver(mx, my, 675, 505, 175, 53) && !inOptions){
            game.gameState = Game.STATE.OptionsInGame;
        }

        //back button in game options
        if (mouseOver(mx, my, 705, 645, 110, 53) && inOptions){
            game.gameState = Game.STATE.Pause;
        }

        //quit button in game pause
        if (mouseOver(mx, my, 705, 645, 110, 53) && !inOptions){
            game.gameState = Game.STATE.PopUp;
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

    public void tick(){
        if (game.gameState == Game.STATE.Pause){
            inOptions = false;
        }else if (game.gameState == Game.STATE.OptionsInGame){
            inOptions = true;
        }
    }

    public void paintBack(Graphics g, Color color){
        g.setColor(color);
        g.drawString("Back", 705, 690);
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font pausedFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Alien Eclipse Italic.ttf")).deriveFont(110f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Alien Eclipse Italic.ttf")));
        Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(50f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));
        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(0, 225, 255);
        Color color2 = new Color(49, 48, 49, 4);
        Color color4 = new Color(255, 0, 204);
        Color color5 = new Color(28, 0, 100, 255);

        g.setFont(pausedFont);

        //background + box
        g.setColor(color2);
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(color5);
        g.fillRect(450, 100, 650, 675);
        g.setColor(Color.black);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(450, 100, 650, 675);

        g.setColor(color1);
        g.drawString("PAUSED", 560, 250);

        g.setFont(buttonFont);
        g.setColor(color4);

        if (game.gameState == Game.STATE.Pause || game.gameState == Game.STATE.PopUp){
            g.drawString("Continue", 665, 410);
            //g.drawRect(665, 365, 200, 53);
            g.drawString("Options", 675, 550);
            //g.drawRect(675, 505, 175, 53);
            g.drawString("Quit", 705, 690);
            //g.drawRect(705, 645, 110, 53);
        }else if (game.gameState == Game.STATE.OptionsInGame){
            g.drawString("Back", 705, 690);
        }

    }
}
