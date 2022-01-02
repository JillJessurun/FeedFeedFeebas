import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartCountdown {

    public int timer = 0;

    public void tick(){
        timer++;
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(150f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));

        g.setFont(pokemonFont);
        Color color = new Color(255, 0, 184, 255);
        g.setColor(color);

        if (timer >= 0 && timer < 60){
            g.drawString("3", 730, 470);
        }else if (timer >= 60 && timer< 120){
            g.drawString("2", 730, 470);
        }else if (timer >= 120 && timer < 180){
            g.drawString("1", 745, 473);
        }else if (timer >=180 && timer < 260){
            g.drawString("FEED FEEBAS!", 420, 470);
        }
    }
}
