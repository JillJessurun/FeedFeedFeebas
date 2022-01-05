import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Popup extends MouseAdapter {

    private Game game;
    private Graphics g;
    private HUD hud;
    private Countdown startCountdown;
    private Menu menu;
    private Handler handler;
    private Level1 level1;

    public Popup(Game game, HUD hud, Countdown startCountdown, Menu menu, Handler handler, Level1 level1){
        this.game = game;
        this.hud = hud;
        this.startCountdown = startCountdown;
        this.menu = menu;
        this.handler = handler;
        this.level1 = level1;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        //pop up back button
        if (mouseOver(mx, my, 808, 409, 110, 53) && game.gameState == Game.STATE.PopUp){
            if (game.inGame) {
                game.gameState = Game.STATE.Pause;
            }else{
                game.gameState = Game.STATE.Menu;
            }
        }

        //pop up quit button
        if (mouseOver(mx, my, 634, 409, 110, 53) && game.gameState == Game.STATE.PopUp){
            if (game.inGame) {
                g.clearRect(0, 0, 1920, 1080);
                game.inGame = false;
                game.gameState = Game.STATE.Menu;
                menu.explosion = false;
                hud.resetHUD();
                startCountdown.resetData();
                game.removedAllObjects = false;
                level1.timer = 0;
                level1.level15Reached = false;
                level1.level15Started = false;
                level1.timerEnd = 100;

                //remove all enemies
                handler.object = handler.getListWithoutEnemies(handler.object);

                //audio
                try {
                    game.foodAudio.stopMusic();
                    game.level1Audio.stopMusic();
                    game.mainAudio.startMusic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{
                System.exit(0);
            }
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

    }

    public void render(Graphics g){
        this.g = g;
        Font font = new Font("Arial", Font.BOLD, 50);
        Font font2 = new Font("Arial", Font.BOLD, 35);
        Font font3 = new Font("Arial", Font.BOLD, 19);
        Color color6 = new Color(86, 86, 86, 255);
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.lightGray);
        g.fillRect(575, 300, 400, 200);

        //stroke popup
        g.setColor(Color.black);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(575, 300, 400, 200);

        //text popup
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Are you sure?", 610, 370);
        //g.setFont(font3);
        //g.setColor(color6);
        //g.drawString("~ unsaved progress will be lost ~", 624, 400);

        //text quit
        //g2d.setStroke(new BasicStroke(2));
        g.setFont(font2);
        //g.setColor(Color.white);
        //g.fillRect(634, 419, 110, 53);
        g.setColor(Color.black);
        g.drawString("Quit", 646, 460);
        //g2d.drawRect(634, 419, 110, 53);

        //text back
        g.setColor(Color.white);
        //g.fillRect(808, 419, 110, 53);
        g.setColor(Color.black);
        g.drawString("Back", 814, 460);
        //g2d.drawRect(808, 419, 110, 53);

    }
}
