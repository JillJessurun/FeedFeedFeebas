import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class LevelMenu extends MouseAdapter {

    private Game game;

    public LevelMenu(Game game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //level 1 button
        if (mouseOver(mx, my, 238, 325, 105, 53) && game.gameState == Game.STATE.LevelMenu) {

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
        g.drawString("World 2", 430, 360);
        g.drawString("World 3", 630, 360);
     

        //buttons
        g.setColor(color2);
        g.setFont(buttonFont2);
        //world 1 levels
        g.drawString("  Level 1", 240, 400);
        g.drawString("  Level 2", 240, 430);
        g.drawString("  Level 3", 240, 460);
        //world 2 levels
        g.drawString("  Level 1", 430, 400);
        g.drawString("  Level 2", 430, 430);
        g.drawString("  Level 3", 430, 460);
        //world 3 levels
        g.drawString("  Level 1", 630, 400);
        g.drawString("  Level 2", 630, 430);
        g.drawString("  Level 3", 630, 460);
    }
}
