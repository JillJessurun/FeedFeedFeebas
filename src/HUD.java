import java.awt.*;

public class HUD {
    public static int HEALTH = 0; //goes to 10000 -> then feebas dies
    public static int HEALTHPERCENTAGE = 0;

    public void tick(){
        HEALTH = (int)Game.clamp(HEALTH, 0, 10000);
        HEALTH = HEALTH + 5;
        HUD.HEALTHPERCENTAGE = HUD.HEALTH / 100;
    }

    public void render(Graphics g, Graphics2D g2d){
        g2d.setColor(Color.gray);
        g2d.fillRoundRect(40, 40, 500, 30, 20, 30);

        Color color = new Color(59, 234, 0);
        Color color2 = new Color(234, 187, 0);
        Color color3 = new Color(234, 0, 0);

        if (HEALTH >= 0 && HEALTH < 5000) {
            g2d.setColor(color);
        }else if (HEALTH >= 5000 && HEALTH < 8000) {
            g2d.setColor(color2);
        }else if (HEALTH >= 8000) {
            g2d.setColor(color3);
        }
        g2d.fillRoundRect(40, 40, HEALTH / 20, 30, 20, 30);

        g2d.setColor(Color.white);
        g2d.drawRoundRect(40, 40, 500, 30, 20, 30);

        //show health percentage:
        Font font = new Font("Courier", Font.BOLD,20);
        g.setFont(font);
        if (HEALTH < 10000) {
            g.drawString("HUNGER  =  " + HEALTHPERCENTAGE, 560, 62);
        }else{
            g.drawString("-  DEAD  -", 560, 62);
        }
    }
}
