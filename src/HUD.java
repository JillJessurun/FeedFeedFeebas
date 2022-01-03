import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HUD {
    public static int HEALTH = 0; //goes to 10000 -> then feebas dies
    public static int HEALTHPERCENTAGE = 0;
    public static int EATSCORE = 0;
    public static int CHANSEYRATE = 0;
    public boolean koffingHit = false;
    private int timer = 60;
    private Game game;
    private BufferedImage chansey;
    private int chanseytimer = 0;
    private boolean chanseyGreen = false;
    private MakeTransparent makeTransparent;
    private Menu menu;

    public HUD(Game game, BufferedImage chansey, MakeTransparent makeTransparent, Menu menu){
        this.game = game;
        this.chansey = chansey;
        this.makeTransparent = makeTransparent;
        this.menu = menu;
    }

    public void tick(){
        //audio
        if (menu.playPressed) {
            try {
                game.loadingAudio.stopMusic();
                game.ingameAudio.startMusic();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            menu.playPressed = false;
        }

        HEALTH = (int)Game.clamp(HEALTH, 0, 10000);
        if (!koffingHit) {
            HEALTH = HEALTH + 5;
            HUD.HEALTHPERCENTAGE = HUD.HEALTH / 100;
        }else{
            timer--;
            HEALTH = HEALTH + 30;
            HUD.HEALTHPERCENTAGE = HUD.HEALTH / 100;
            if (timer == 0){
                timer = 60;
                koffingHit = false;
            }
        }

        //chansey rate setup
        CHANSEYRATE++;
        if (CHANSEYRATE >= 1000){
            CHANSEYRATE = 1000;
            if (chanseytimer == 10){
                chanseytimer = 0;
                if (chanseyGreen){
                    chanseyGreen = false;
                }else{
                    chanseyGreen = true;
                }
            }else{
                chanseytimer++;
            }
        }
    }

    public void render(Graphics g, Graphics2D g2d) throws IOException, FontFormatException {
        //health bar
        g2d.setColor(Color.gray);
        g2d.fillRoundRect(40, 40, 500, 30, 20, 30);

        //chansey bar
        g2d.setColor(Color.gray);
        g2d.fillRoundRect(1465, 40, 30, 200, 20, 30);

        //all different colors
        Color color = new Color(59, 234, 0);
        Color color2 = new Color(234, 187, 0);
        Color color3 = new Color(234, 0, 0);

        //health
        if (HEALTH >= 0 && HEALTH < 5000) {
            g2d.setColor(color);
        }else if (HEALTH >= 5000 && HEALTH < 8000) {
            g2d.setColor(color2);
        }else if (HEALTH >= 8000) {
            g2d.setColor(color3);
        }
        g2d.fillRoundRect(40, 40, HEALTH / 20, 30, 20, 30);

        //chansey health
        if (CHANSEYRATE == 1000){
            if (chanseyGreen){
                g2d.setColor(Color.green);
            }else{
                g2d.setColor(Color.pink);
            }
        }else {
            g2d.setColor(Color.pink);
        }
        g2d.fillRoundRect(1465, 40, 30, CHANSEYRATE / 5, 20, 30);

        //border around health bar
        g2d.setColor(Color.white);
        g2d.drawRoundRect(40, 40, 500, 30, 20, 30);

        //border around chansey bar
        g2d.drawRoundRect(1465, 40, 30, 200, 20, 30);

        //little hud chansey
        int colour = chansey.getRGB(0, 0);
        g2d.drawImage(makeTransparent.makeColorTransparent(chansey, new Color(colour)), 1440, CHANSEYRATE / 5, null);

        //pokemon font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(30f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));

        //displaying hunger rate + score
        g.setFont(pokemonFont);
        if (HEALTH < 10000) {
            if (HEALTH >= 0 && HEALTH < 5000) {
                g2d.setColor(color);
            }else if (HEALTH >= 5000 && HEALTH < 8000) {
                g2d.setColor(color2);
            }else if (HEALTH >= 8000) {
                g2d.setColor(color3);
            }
            g.drawString("HUNGER  =  " + HEALTHPERCENTAGE, 560, 62);
            g.setColor(Color.magenta);
            g.drawString("EAT SCORE  =  " + EATSCORE, 760, 62);
        }else{
            g.setColor(Color.red);
            g.drawString("-  DEAD  -", 560, 62);
            game.gameover = true;
        }
    }

    public void setEATSCORE(int EATSCORE) {
        HUD.EATSCORE = EATSCORE;
    }

    public void setHEALTH(int newHEALTH) {
        HEALTH = newHEALTH;
    }

    public int getHEALTH() {
        return HEALTH;
    }

    public void setCHANSEYRATE(int newCHANSEYRATE){CHANSEYRATE = newCHANSEYRATE;}

    public int getCHANSEYRATE() {
        return CHANSEYRATE;
    }

    public static void setHEALTHPERCENTAGE(int HEALTHPERCENTAGE) {
        HUD.HEALTHPERCENTAGE = HEALTHPERCENTAGE;
    }

    public void setKoffingHit(boolean koffingHit) {
        this.koffingHit = koffingHit;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void setChanseytimer(int chanseytimer) {
        this.chanseytimer = chanseytimer;
    }

    public void setChanseyGreen(boolean chanseyGreen) {
        this.chanseyGreen = chanseyGreen;
    }

    public int getEATSCORE() {
        return EATSCORE;
    }

    public void resetHUD(){
        setHEALTH(0);
        setHEALTHPERCENTAGE(0);
        setEATSCORE(0);
        setCHANSEYRATE(0);
        setKoffingHit(false);
        setTimer(60);
        setChanseytimer(0);
        setChanseyGreen(false);
    }
}
