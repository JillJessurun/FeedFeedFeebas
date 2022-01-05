import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Handler extends JPanel implements ActionListener {
    LinkedList<GameObject> object = new LinkedList<GameObject>();
    private Game game;
    private BufferedImage background;

    public Handler(Game game, BufferedImage background){
        this.game = game;
        this.background = background;
    }

    public void tick(){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    /*
    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

     */

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public LinkedList<GameObject> getListWithoutEnemies(LinkedList<GameObject> oldList){
        LinkedList<GameObject> newList = new LinkedList<GameObject>();
        for (int i = 0; i < oldList.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Chansey || tempObject.getId() == ID.Player || tempObject.getId() == ID.Food) {
                newList.add(tempObject);
            }
        }
        return newList;
    }

    public void renderMenu(Graphics g, float x, float y, BufferedImage feebasBG, BufferedImage feebasSprite, BufferedImage food, BufferedImage feebasMoving, MakeTransparent makeTransparent, Game game, boolean explosion, Menu menu, float x2, float y2) throws IOException, FontFormatException {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

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
            menu.drawButton(238, 325, 105, 53, 240, 370, "Play", g);
            menu.drawButton(238, 485, 180, 53, 240, 530, "Options", g);
            menu.drawButton(238, 645, 110, 53, 240, 690, "Quit", g);
        }else if (game.gameState == Game.STATE.Options){
            menu.drawButton(240, 298, 290, 50, 240, 340, "Change name", g);
            menu.drawButton(240, 398, 143, 45, 240, 440, "Sound", g);
            menu.drawButton(240, 498, 240, 50, 240, 540, "Highscores", g);
            menu.drawButton(240, 598, 235, 50, 240, 640, "New game", g);
            menu.drawButton(240, 695, 110, 45, 240, 740, "Back", g);
        }

        //suicide feebas
        g.drawImage(makeTransparent.makeColorTransparent(feebasMoving, new Color(colour)), (int)x, (int)y, null);
        g.drawImage(makeTransparent.makeColorTransparent(food, new Color(colour)), (int)x2, (int)y2, null);

        //BufferedImage bufferedImage = makeTransparent.imageToBufferedImage(makeTransparent.makeColorTransparent(feebasMoving, new Color(colour)));
        //handler.renderMenu(g, (int)x, (int)y, bufferedImage);

        //gif
        if (explosion) {
            g.drawImage(menu.image, 630, 465, null);
        }

        // g2d.drawImage(image, (int)x, (int)y, null);
        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (game.gameState == Game.STATE.Level1 || game.gameState == Game.STATE.Level2 || game.gameState == Game.STATE.Pause){
            g.drawImage(background, 0, 0, null);
        }
        for (GameObject tempObject : object) {
            tempObject.render(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
