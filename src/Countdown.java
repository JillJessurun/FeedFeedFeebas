import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Countdown {

    public static int timer = 0;
    private static float fontTimer = 90f;//150 is perfect

    //x axes per text
    private static float x3 = 760;
    private static float x2 = 760;
    private static float x1 = 767;
    private static float xFF = 490;

    //booleans
    private static boolean timer3 = false;
    private static  boolean timer2 = false;
    private static boolean timer1 = false;
    private static boolean feedFeebas = false;

    public void tick(){
        fontTimer = fontTimer + 1;
        if (fontTimer >= 150){
            if (!feedFeebas) {
                fontTimer = 90;
            }else{
                fontTimer = 150;
            }
        }

        if (timer3){
            x3 = x3 - 0.3f;
        }else if (timer2){
            x2 = x2 - 0.3f;
        }else if (timer1){
            //x1 = x1 - -0.5f;
        }else if (feedFeebas){
            if (fontTimer != 150) {
                xFF = xFF - 1;
            }
        }

        timer++;
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(fontTimer);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));

        g.setFont(pokemonFont);
        Color color = new Color(252, 207, 239, 255);
        g.setColor(color);

        if (timer >= 0 && timer < 60){
            timer3 = true;
            g.drawString("3", (int) x3, 470);
        }else if (timer >= 60 && timer< 120){
            timer3 = false;
            timer2 = true;
            g.drawString("2", (int) x2, 470);
        }else if (timer >= 120 && timer < 180){
            timer2 = false;
            timer1 = true;
            g.drawString("1", (int) x1, 473);
        }else if (timer >=180 && timer < 260){
            timer1 = false;
            feedFeebas = true;
            g.drawString("FEED FEEBAS!", (int) xFF, 470);
        }
    }

    public void resetData(){
        timer = 0;
        fontTimer = 90f;//150 is perfect

        //x axes per text
        x3 = 760;
        x2 = 760;
        x1 = 767;
        xFF = 490;

        //booleans
        timer3 = false;
        timer2 = false;
        timer1 = false;
        feedFeebas = false;
    }

}
