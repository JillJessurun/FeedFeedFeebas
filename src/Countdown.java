import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Countdown {

    public static int timer = 0;
    private static float fontTimer = 90f;//150 is perfect

    //gifs
    //private Image image;
    public Image image2;
    public Image image3;

    //x axes per text
    private static float xFF = 450;

    //booleans
    private static boolean feedFeebas = false;

    public Countdown(){

    }

    public void tick(){
        fontTimer = fontTimer + 1;
        if (fontTimer >= 150){
            if (!feedFeebas) {
                fontTimer = 90;
            }else{
                fontTimer = 150;
            }
        }

        if (feedFeebas){
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
        Color color = new Color(198, 0, 241);
        g.setColor(color);

        if (timer >= 0 && timer < 100){
            //gifs
            g.drawImage(image2, 530, 200, null);
        }else if (timer >=130 && timer < 260){
            feedFeebas = true;
            g.drawString("FEED FEEBAS!", (int) xFF, 470);
            g.drawImage(image3, 660, 580, null);
        }
    }

    public void resetData(){
        timer = 0;
        fontTimer = 90f;
        xFF = 490;
        feedFeebas = false;
    }

    public void setImage3(Image image3) {
        this.image3 = image3;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }
}
